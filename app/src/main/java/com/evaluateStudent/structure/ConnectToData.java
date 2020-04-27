package com.evaluateStudent.structure;

import android.content.Context;
import android.widget.Toast;

import com.evaluateStudent.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;


public class ConnectToData {

    private static File STORAGE_DATA;

    public static void createData(Context context) {
        String defaultData;
        File checkExist;

        STORAGE_DATA = new File(context.getExternalFilesDir(null), "EvaluateStudent");

        if (!STORAGE_DATA.isDirectory()) {
            STORAGE_DATA.mkdir();
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

    public static ArrayList<Standard> getListStandard(int type) {

        ArrayList<Standard> listStd = new ArrayList<>();
        JSONObject data;

        try {
            data = new JSONObject(readDataFromFile("standard"));
            JSONArray access = data.names();

            for (int i = 0; i < access.length(); i++) {
                JSONObject standardObj = data.getJSONObject(access.getString(i));
                Standard standard = new Standard();

                standard.setId(standardObj.getInt("standardId"));
                standard.setContent(standardObj.getString("name"));
                standard.setWeight(standardObj.getInt("weight"));
                standard.setType(standardObj.getInt("type"));
                standard.setListCriteria(getListCriterias(standardObj.getJSONObject("listCriterion")));

                if(standard.getType() <= type) {
                    listStd.add(standard);
                }
            }
        } catch (JSONException e) {
        }

        return listStd;
    }

    private static ArrayList<Criterion> getListCriterias(JSONObject data) throws JSONException {
        ArrayList<Criterion> listCriterias = new ArrayList<>();
        JSONArray access = data.names();

        for (int i = 0; i < access.length(); i++) {
            JSONObject criteriaObj = data.getJSONObject(access.getString(i));
            Criterion criteria = new Criterion();

            criteria.setId(criteriaObj.getInt("criterionId"));
            criteria.setContent(criteriaObj.getString("name"));
            criteria.setWeight(criteriaObj.getInt("weight"));
            criteria.setListAction(getListActions(criteriaObj.getJSONObject("listAction")));

            listCriterias.add(criteria);
            System.out.println(criteria.toString());
        }

        return listCriterias;
    }

    private static ArrayList<Action> getListActions(JSONObject data) throws JSONException {
        ArrayList<Action> listActions = new ArrayList<>();
        JSONArray access = data.names();

        for (int i = 0; i < access.length(); i++) {
            JSONObject actionObj = data.getJSONObject(access.getString(i));
            Action action = new Action();

            action.setId(actionObj.getInt("actionId"));
            action.setContent(actionObj.getString("name"));
            action.setWeight(actionObj.getInt("weight"));

            listActions.add(action);
            System.out.println(action.toString());
        }

        return listActions;
    }

    public static void saveEvaluate(ArrayList<Standard> listStandard) {

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
        String ret = null;

        try {
            File readFile = new File(STORAGE_DATA, typeData);
            Scanner sc = new Scanner(readFile, StandardCharsets.UTF_8.name());

            ret = sc.nextLine();
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
}
