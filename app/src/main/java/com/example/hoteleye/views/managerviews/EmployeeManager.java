package com.example.hoteleye.views.managerviews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.hoteleye.adapters.EmployeeManagerAdapter;
import com.example.hoteleye.databases.entities.Account;
import com.example.hoteleye.databases.entities.Employee;
import com.example.hoteleye.commons.MyUtils;
import com.example.hoteleye.R;
import com.example.hoteleye.views.receptionviews.ReceptionHome;
import com.example.hoteleye.viewmodels.EmployeeManagerItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.util.ArrayList;

public class EmployeeManager extends AppCompatActivity {
    private ListView listView;
    private FloatingActionButton add_fab;

    ArrayList<EmployeeManagerItem> employeeManagerItems  = new ArrayList<>();;
    EmployeeManagerAdapter adapter;

    FirebaseFirestore database;
    String personID, employeeID, accountID;
    int idCard;
    String name, date, account, phoneNumber;
    int i,ii;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_manager);
        init();
    }
    private void init() {
        listView = findViewById(R.id.listview_employee_manager);
        add_fab = findViewById(R.id.floatingActionButton_employee_manager);
        database = FirebaseFirestore.getInstance();


//        employeeManagerItems.add(new CustomerManagerItem(1,"thanhphan","25/10/1998","0396065919",(float)0.01,"VIP"));

        adapter = new EmployeeManagerAdapter(getApplicationContext(), employeeManagerItems);
        listView.setAdapter(adapter);
        fillList();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //int idCard, String name, String date, String phoneNumber, String account
                EmployeeManagerItem item = employeeManagerItems.get(position);
                Employee employee = new Employee(item.getPersonID(),item.getEmployeeID());

                employee.setId_card(item.getIdCard());
                employee.setDateOfBirth(item.getDate());
                employee.setPhone_number(item.getPhoneNumber());
                employee.setFullName( item.getName());

                Account account = new Account(item.getAccountID());
                account.setUsername(item.getAccount());
                employee.setAccount(account);

                Intent itent = new Intent(EmployeeManager.this, EmployeeDetail.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                itent.putExtra("employee", employee);
                startActivity(itent);
            }
        });

        add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeManager.this, EmployeeAdd.class)
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });


    }
//    public boolean onKeyDown(int keyCode, KeyEvent event){
//        if (keyCode == KeyEvent.KEYCODE_BACK)
//        {
////            switchView();
//        }
//        return false;
//    }

    private void fillList() {
        CollectionReference collectionReference = database.collection("employee");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
//                              int idCard, String name, String date, String phoneNumber, String account
                            employeeManagerItems.add
                                    (new EmployeeManagerItem(doc.getId(),
                                            String.valueOf(doc.get("person")),
                                            String.valueOf(doc.get("account"))));

                        adapter.notifyDataSetChanged();
                    }

                    CollectionReference collectionReference = database.collection("person");
                    collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    personID = doc.getId();
                                    idCard = Integer.parseInt(String.valueOf(doc.get("idcard")));
                                    name = String.valueOf(doc.get("fullname"));
                                    phoneNumber = String.valueOf(doc.get("phonenumber"));
                                    try {
                                        date =  MyUtils.covertTimestamptoString((Timestamp) doc.get("dateofbirth"));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    for (i = 0; i < employeeManagerItems.size(); i++
                                    ) {
                                        if (employeeManagerItems.get(i).getPersonID().equals(personID)){
                                            employeeManagerItems.get(i).setIdCard(idCard);
                                            employeeManagerItems.get(i).setName(name);
                                            employeeManagerItems.get(i).setPhoneNumber(phoneNumber);
                                            employeeManagerItems.get(i).setDate(date);
                                            adapter.notifyDataSetChanged();
                                        }
                                    }

                                }
                            }
                        }});

                    CollectionReference collectionReference1 = database.collection("account");
                    collectionReference1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    accountID = doc.getId();

                                    account = String.valueOf(doc.get("username"));

                                    for (ii = 0; ii < employeeManagerItems.size(); ii++
                                    ) {
                                        if (employeeManagerItems.get(ii).getAccountID().equals(accountID)){
                                            employeeManagerItems.get(ii).setAccount(account);
                                            adapter.notifyDataSetChanged();
                                        }
                                    }

                                }
                            }
                        }});
                }
            }
        });
    }

    private void switchView() {
        Intent itent1 = getIntent();
        String tagg = itent1.getStringExtra("tagg");
        if(tagg.equals("reception")){
            Intent intent = new Intent(EmployeeManager.this, ReceptionHome.class)
                    .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }else if(tagg.equals("manager")){
            Intent intent = new Intent(EmployeeManager.this, ManagerHome.class)
                    .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }

    }
}

