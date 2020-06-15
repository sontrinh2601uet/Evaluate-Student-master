package com.evaluateStudent.structure;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.evaluateStudent.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;


public class ConnectToData {

    public static ArrayList<Standard> listStandard;
    private static File STORAGE_DATA;

    public static void createData(Context context) {
        String defaultData;
        File checkExist;

        STORAGE_DATA = new File(context.getExternalFilesDir(null), "EvaluateStudent");

        if (!STORAGE_DATA.isDirectory()) {
            STORAGE_DATA.mkdirs();
        }
        boolean a = STORAGE_DATA.exists();
        checkExist = new File(STORAGE_DATA, "user");
        if (!checkExist.isFile()) {
            defaultData = context.getString(R.string.def_user);
            writeDataIntoFile(checkExist.getAbsolutePath(), defaultData);
        }

        checkExist = new File(STORAGE_DATA, "student");
        if (!checkExist.isFile()) {
            defaultData = context.getString(R.string.def_student);
            writeDataIntoFile(checkExist.getAbsolutePath(), defaultData);
        }

        checkExist = new File(STORAGE_DATA, "standard");
        if (!checkExist.isFile()) {
            defaultData = context.getString(R.string.def_data);
            writeDataIntoFile(checkExist.getAbsolutePath(), defaultData);
        }
    }

    public static String getDataFile() {
        return readDataFromFile("evaluateHistory.txt");
    }

    public static int getAuthenticationAccess(Context context, String name, String password) {
        JSONObject data;

        try {
            data = new JSONObject(readDataFromFile("user"));
        } catch (JSONException e) {
            Toast.makeText(context, "Lỗi! Dữ liệu không tồn tại!", Toast.LENGTH_LONG).show();
            return -1;
        }

        try {
            JSONObject hasUser = data.getJSONObject(name);

            if (hasUser.getString("pass").equals(password)) {
                return hasUser.getInt("type");
            } else {
                return -1;
            }
        } catch (JSONException e) {
            return -1;
        }
    }

    public static JSONObject getStudentInfo(String studentId, Context context) {
        JSONObject studentInfo;

        try {
            studentInfo = new JSONObject(readDataFromFile("student"));
        } catch (JSONException e) {
            Toast.makeText(context, "Lỗi! Dữ liệu không tồn tại!", Toast.LENGTH_LONG).show();
            return null;
        }

        try {
            JSONObject hasStudent = studentInfo.getJSONObject(studentId);

            return hasStudent;
        } catch (JSONException e) {
            return null;
        }
    }

    public static void getListStandard(int type) {
        listStandard = new ArrayList<>();
        JSONObject data;

        try {
            data = new JSONObject(readDataFromFile("standard"));
            JSONArray access = data.names();

            for (int i = 0; i < access.length(); i++) {
                JSONObject standardObj = data.getJSONObject(access.getString(i));
                Standard standard = new Standard();

                standard.setId(standardObj.getInt("standardId"));
                standard.setContent(standardObj.getString("name"));
                standard.setWeight(standardObj.getDouble("weight"));
                standard.setType(standardObj.getInt("type"));
                standard.setListCriteria(getListCriteria(standardObj.getJSONObject("listCriterion")));

                if (standard.getType() <= type) {
                    listStandard.add(standard);
                }
            }
        } catch (JSONException e) {
        }
    }

    private static ArrayList<Criterion> getListCriteria(JSONObject data) throws JSONException {
        ArrayList<Criterion> listCriteria = new ArrayList<>();
        JSONArray access = data.names();

        for (int i = 0; i < access.length(); i++) {
            JSONObject criteriaObj = data.getJSONObject(access.getString(i));
            Criterion criteria = new Criterion();

            criteria.setId(criteriaObj.getInt("criterionId"));
            criteria.setContent(criteriaObj.getString("name"));
            criteria.setWeight(criteriaObj.getDouble("weight"));
            criteria.setListAction(getListActions(criteriaObj.getJSONObject("listAction")));

            listCriteria.add(criteria);
            System.out.println(criteria.toString());
        }

        return listCriteria;
    }

    private static ArrayList<Action> getListActions(JSONObject data) throws JSONException {
        ArrayList<Action> listActions = new ArrayList<>();
        JSONArray access = data.names();

        for (int i = 0; i < access.length(); i++) {
            JSONObject actionObj = data.getJSONObject(access.getString(i));
            Action action = new Action();

            action.setId(actionObj.getInt("actionId"));
            action.setContent(actionObj.getString("name"));
            action.setWeight(actionObj.getDouble("weight"));

            listActions.add(action);
        }

        return listActions;
    }

    public static boolean saveEvaluate(String studentId, SharedPreferences pref) {
        SharedPreferences.Editor editor = pref.edit();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        File saveEvaluate = new File(STORAGE_DATA, "evaluateHistory.txt");

        int indexId = pref.getInt("indexId", 0);
        int hasEvaluateStd = 0;
        double point = 0;
        StringBuilder dataEvaluate = new StringBuilder();
        try {
            FileOutputStream fos = new FileOutputStream(saveEvaluate, true);

            dataEvaluate.append(sdf.format(new Date())).append(",");
            dataEvaluate.append(pref.getString("email", "")).append(",");
            dataEvaluate.append(studentId).append(",");

            StringBuilder listActionEvaluated = new StringBuilder();
            for (Standard standard : listStandard) {
                standard.setPoint();
                point += standard.point;
                hasEvaluateStd += (standard.point == 0) ? 0 : 1;

//                for (Criterion criterion : standard.getListCriteria()) {
//                    for (Action action : criterion.getListAction()) {
//                        if (action.getRateQuality() != 0) {
//
//                        }
//                    }
//                }

                if (standard.point != 0.0) {
                    listActionEvaluated.append("$").append(standard.getId()).append("~").append(new DecimalFormat("#.##").format(standard.point));
                }
            }

            if(hasEvaluateStd == 0) {
                fos.close();
                return false;
            }

            dataEvaluate.append(String.format("%.1f", point / hasEvaluateStd)).append(",");
            dataEvaluate.append(listActionEvaluated.toString()).append(";\n");
            fos.write(dataEvaluate.toString().getBytes());

            editor.putInt("indexId", indexId + 1);
            editor.commit();
            clearEvaluate();
            fos.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    private static void clearEvaluate() {
        if (listStandard != null) {
            for (Standard standard : listStandard) {
                for (Criterion criterion : standard.getListCriteria()) {
                    for (Action action : criterion.getListAction()) {
                        action.resetRateQuality();
                    }
                }
            }
        }
    }


    private static void writeDataIntoFile(String path, String defaultData) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(path));
            fos.write(defaultData.getBytes());
            fos.close();
        } catch (IOException e) {
        }
    }

    private static String readDataFromFile(String typeData) {
        StringBuilder ret = new StringBuilder();

        try {
            File readFile = new File(STORAGE_DATA, typeData);
            Scanner sc = new Scanner(readFile, StandardCharsets.UTF_8.name());


            while(sc.hasNext()) {
                ret.append(sc.nextLine());
            }
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return ret.toString();
    }

    public static Standard getStandardById(int id) {
        for (Standard standard : listStandard) {
            if(standard.getId() == id) {
                return standard;
            }
        }

        return null;
    }
}
