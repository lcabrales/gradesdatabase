package com.lucascabrales.gradesdatabase;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.lucascabrales.gradesdatabase.adapters.GradesAdapter;
import com.lucascabrales.gradesdatabase.data.StorageManager;
import com.lucascabrales.gradesdatabase.models.Grade;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    public static final String SUBJECT = "Asignatura";
    public static final String YEAR = "Año";
    public static final String GRADE = "Nota";
    public static final String EXAM = "Examen Final";

    private MainActivity mContext = this;
    private ListView mListView;
    private ArrayList<Grade> mDataset;
    private GradesAdapter mAdapter;
    private AlertDialog mDialog;
    private ArrayList<String> mSpinnerData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupList();
        setupSpinner();
    }

    private void setupSpinner() {
        final Spinner spinner = (Spinner) findViewById(R.id.sp_order);

        mSpinnerData = new ArrayList<>();
        mSpinnerData.add(SUBJECT);
        mSpinnerData.add(YEAR);
        mSpinnerData.add(GRADE);
        mSpinnerData.add(EXAM);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, mSpinnerData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        String defaultOrderBy = StorageManager.getOrderBy();
        spinner.setSelection(mSpinnerData.indexOf(defaultOrderBy));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                mDataset = Grade.getList(mSpinnerData.get(position));
                mAdapter.setData(mDataset);

                StorageManager.setOrderBy(mSpinnerData.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setupList() {
        mListView = (ListView) findViewById(R.id.list_view);

        mDataset = Grade.getList();

        mAdapter = new GradesAdapter(mContext, mDataset);
        mListView.setAdapter(mAdapter);
        mListView.setEmptyView(findViewById(R.id.empty_view));
        mListView.setOnItemClickListener(mContext);
        mListView.setOnItemLongClickListener(mContext);
    }

    private void addGrade() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(R.layout.dialog_add_grade);
        builder.setCancelable(false);

        builder.setTitle("Añadir Nota");

        DialogInterface.OnClickListener accept = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Grade obj = new Grade();
                obj.subject = ((EditText) mDialog.findViewById(R.id.et_subject)).getText().toString();
                obj.year = ((EditText) mDialog.findViewById(R.id.et_year)).getText().toString();
                obj.grade = ((EditText) mDialog.findViewById(R.id.et_grade)).getText().toString();
                obj.exam = ((EditText) mDialog.findViewById(R.id.et_exam)).getText().toString();

                Grade.addObj(obj);

                updateData();
            }
        };

        builder.setPositiveButton("Añadir", accept);
        builder.setNegativeButton("Cancelar", null);

        mDialog = builder.create();
        mDialog.show();
    }

    private void editGrade(Grade obj) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(R.layout.dialog_add_grade);
        builder.setCancelable(false);

        builder.setTitle("Editar Nota");

        DialogInterface.OnClickListener accept = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Grade obj = new Grade();
                obj.subject = ((EditText) mDialog.findViewById(R.id.et_subject)).getText().toString();
                obj.year = ((EditText) mDialog.findViewById(R.id.et_year)).getText().toString();
                obj.grade = ((EditText) mDialog.findViewById(R.id.et_grade)).getText().toString();
                obj.exam = ((EditText) mDialog.findViewById(R.id.et_exam)).getText().toString();

                Grade.updateObj(obj);

                updateData();
            }
        };

        builder.setPositiveButton("Editar", accept);
        builder.setNegativeButton("Cancelar", null);

        mDialog = builder.create();
        mDialog.show();

        mDialog.findViewById(R.id.et_subject).setEnabled(false);
        ((EditText) mDialog.findViewById(R.id.et_subject)).setText(obj.subject);
        ((EditText) mDialog.findViewById(R.id.et_year)).setText(obj.year);
        ((EditText) mDialog.findViewById(R.id.et_grade)).setText(obj.grade);
        ((EditText) mDialog.findViewById(R.id.et_exam)).setText(obj.exam);
    }

    private void deleteGrade(Grade obj) {
        final Grade grade = obj;

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setTitle("Eliminar Nota");

        String message = "¿Desea eliminar el registro de la asignatura " + obj.subject + "?";
        builder.setMessage(message);

        builder.setCancelable(false);

        DialogInterface.OnClickListener accept = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Grade.deleteObj(grade);

                updateData();
            }
        };

        builder.setPositiveButton("Sí", accept);
        builder.setNegativeButton("No", null);

        mDialog = builder.create();
        mDialog.show();
    }

    private void deleteAll() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setTitle("Eliminar Registros");

        String message = "¿Está seguro que desea eliminar todos los registros?";
        builder.setMessage(message);

        builder.setCancelable(false);

        DialogInterface.OnClickListener accept = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Grade.deleteTable();

                updateData();
            }
        };

        builder.setPositiveButton("Sí", accept);
        builder.setNegativeButton("No", null);

        mDialog = builder.create();
        mDialog.show();
    }

    private void updateData() {
        mDataset = Grade.getList();
        mAdapter.setData(mDataset);
    }

    //region Menu
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add) {
            addGrade();
        } else if (id == R.id.delete_all) {
            deleteAll();
        }
        return super.onOptionsItemSelected(item);
    }
    //endregion

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Editar item
        Grade obj = mDataset.get(position);

        editGrade(obj);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        //Eliminar item
        Grade obj = mDataset.get(position);

        deleteGrade(obj);
        return true;
    }
}
