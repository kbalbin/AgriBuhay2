package com.AgriBuhayProj.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.AgriBuhayProj.app.ReusableCode.ReusableCodeForAll;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Logistics_registration extends AppCompatActivity {

    //SPINNER
    //PROVINCE - CITY/MUNICIPALITY
    String [] Davao_de_Oro = {"Compostela", "Laak", "Mabini", "Maco", "Maragusan", "Mawab"
            , "Monkayo", "Montivista", "Nabunturan", "New Bataan", "Pantukan"};
    String [] Davao_del_Sur = {"Bansalan", "Davao City", "Digos", "Hagonoy", "Kiblawan"
            , "Magsaysay", "Malalag", "Matanao", "Padada", "Santa Cruz", "Sulop"};
    String [] Davao_del_Norte = {"Asuncion", "Baraulio E. Dujali", "Carmen", "Kapalong", "New Corella"
            , "Panabo", "Samal", "San Isidro", "Santo Tomas", "Tagum", "Talaingud"};
    String [] Davao_Oriental = {"Baganga", "Banaybanay", "Boston", "Caraga", "Cateel"
            , "Governor Generoso", "Lupon", "Manay", "Mati", "San Isidro (Oriental)"};
    String [] Davao_Occidental = {"Don Marcelino", "Jose Abad Santos", "Malita", "Santa Maria", "Sarangani"};
    //CITY/MUNICIPALITY-BARANGGAY
    //davao de oro
    String [] Compostela = {"Bagongon", "Gabi", "Lagab", "Mangayon", "Mapaca", "Maparat", "New Alegria", "Ngan", "Osme??a", "Panansalan",
            "Poblacion", "San Jose", "San Miguel", "Siocon", "Tamia", "Aurora"};
    String [] Laak = {"Aguinaldo","Amorcruz","Ampawid","Andap","Anitap","Bagong Silang","Banbanon","Belmonte","Binasbas","Bullucan","Ceboleda","Concepcion","Datu Ampunan",
            "Datu Davao","Do??a Josefa","El Katipunan","Il Papa","Imelda","Inakayan","Kaligutan","Kapatagan","Kidawa","Kilagding","Kiokmay","Laak (Poblacion)","Langtud",
            "Longanapan","Mabuhay","Macopa","Malinao","Mangloy","Melale","Naga","New Bethlehem","Panamoren","Sabud","San Antonio","Sta Emilia","Sto Ni??o","Sisimon"};
    String [] Mabini = {"Cadunan","Pindasan","Cuambog (Poblacion)","Tagnanan (Mampising)","Anitapan","Cabuyuan","Del Pilar","Libodon","Golden Valley (Maraut)","Pangibiran",
            "San Antonio"};
    //Research scope area
    String [] Maco = {"Anibongan", "Anislagan", "Binuangan", "Bucana", "Calabcab", "Concepcion", "Dumlan", "Elizalde (Somil)", "Pangi (Gaudencio Antonio)"
            , "Gubatan", "Hijo", "Kinuban", "Langgam", "Lapu-lapu", "Libay-libay", "Limbo", "Lumatab", "Magangit", "Malamodao", "Manipongol"
            , "Mapaang", "Masara", "New Asturias", "Panibasan", "Panoraon", "San Juan", "San Roque", "Sangab", "Taglawig"
            , "Mainit", "New Barili", "New Leyte", "New Visayas", "Panangan", "Tagbaros", "Teresa"};
    String [] Maragusan = {};
    String [] Mawab = {};
    String [] Monkayo = {};
    String [] Montivista = {};
    String [] Nabunturan = {"Anislagan", "Antequera", "Basak", "Bayabas", "Bukal", "Cabacungan", "Cabidianan", "Katipunan"
            , "Libasan", "Linda", "Magading", "Magsaysay", "Mainit", "Manat", "Matilo", "Mipangi", "New Dauis", "New Sibonga", "Ogao"
            , "Pangutosan", "San Isidro", "San Roque", "San Vicente", "Santa Maria", "Santo Ni??o (Kao)", "Sasa", "Tagnocon"};
    String [] New_Bataan = {};
    String [] Pantukan = {};
    //davao del sur
    String [] Bansalan = {};
    String [] Davao_City = {};
    String[] Digos = {"Aplaya", "Balabag", "San Jose (Balutakay)", "Binaton", "Cogon", "Colorado", "Dawis", "Dulangan", "Goma", "Igpit", "Kiagot", "Lungag", "Mahayahay"
            , "Matti", "Kapatagan (Rizal)", "Ruparan", "San Agustin", "San Miguel (Odaca)", "San Roque", "Sinawilan", "Soong", "Tiguman", "Tres De Mayo"};
    String [] Hagonoy = {};
    String [] Kiblawan = {};
    String [] Magsaysay = {};
    String [] Malalag = {};
    String [] Matanao = {};
    String [] Padada = {};
    String [] Santa_Cruz = {};
    String [] Sulop = {};
    //Davao del Norte
    String [] Asuncion = {};
    String [] Baraulio_E_Dujali = {};
    String [] Carmen = {};
    String [] Kapalong = {};
    String [] New_Corella = {};
    String[] Panabo = {"A. O. Floirendo","Datu Abdul Dadia","Buenavista","Cacao","Cagagohan","Consolacion","Dapco","Gredu (Poblacion)","J.P. Laurel","Kasilak","Katipunan","Katualan","Kauswagan",
            "Kiotoy","Little Panay","Lower Panaga (Roxas)","Mabunao","Maduao","Malativas","Manay","Nanyo","New Malaga (Dalisay)","New Malitbog","New Pandan (Poblacion)","New Visayas","Quezon",
            "Salvacion","San Francisco (Poblacion)","San Nicolas","San Pedro","San Roque","San Vicente","Santa Cruz","Santo Ni??o (Poblacion)","Sindaton","Southern Davao","Tagpore","Tibungol",
            "Upper Licanan","Waterfall"};
    String [] Samal = {};
    String [] San_Isidro = {};
    String[] Santo_Tomas = {"Balagunan","Bobongon","Casig-Ang","Esperanza","Kimamon","Kinamayan","La Libertad","Lungaog","Magwawa","New Katipunan","New Visayas","Pantaron","Salvacion",
            "San Jose","San Miguel","San Vicente","Talomo","Tibal-og","Tulalian"};
    String[] Tagum = {"Apokon", "Bincungan", "Busaon", "Canocotan", "Cuambogan", "La Filipina", "Liboganon", "Madaum", "Magdum", "Magugpo Poblacion", "Mankilam"
            , "New Balamban", "Nueva Fuerza", "Pagsabangan", "Pandapan", "San Agustin", "San Isidro", "San Miguel (Camp 4)", "Visayan Village"};
    String [] Talaingud = {};
    //Davao Oriental provincial capital
    String [] Baganga = {};
    String [] Banaybanay = {};
    String [] Boston = {};
    String [] Caraga = {};
    String [] Cateel = {};
    String [] Governor_Generoso = {};
    String [] Lupon = {};
    String [] Manay = {};
    String[] Mati = {"Badas", "Bobon", "Buso", "Cabuaya", "Central (City Proper/Poblacion)", "Culian"
            , "Dahican", "Danao", "Dawan", "Don Enrique Lopez", "Don Martin Marundan", "Don Salvador Lopez, Sr."
            , "Langka", "Lawigan", "Libudon", "Luban", "Macambol", "Mamali", "Matiao", "Mayo"
            , "Sainz", "Sanghay", "Tagabakid", "Tagbinonga", "Taguibo", "Tamisan"};
    String [] San_Isidro_Oriental = {};
    //Davao Occidental provincial capital
    String [] Don_Marcelino = {};
    String [] Jose_Abad_Santos = {};
    String[] Malita = {"Bito", "Bolia", "Buhangin", "Culaman", "Datu Danwata", "Demloc", "Felis", "Fishing Village"
            , "Kibalatong", "Kidalapong", "Kilalag", "Kinangan", "Lacaron", "Lagumit", "Lais", "Little Baguio", "Macol"
            , "Mana", "Manuel Peralta", "New Argao", "Pangian", "Pinalpalan", "Poblacion", "Sangay", "Talogoy", "Tical"
            , "Ticulon", "Tingolo", "Tubalan", "Pangaleon"};
    String [] Santa_Maria = {};
    String [] Sarangani = {};


    TextInputLayout Fname, Lname, Pass, cfpass, mobileno, houseno, area, postcode, Email;
    Spinner statespin, Cityspin, Suburban;
    Button signup, Emaill, Phone;
    CountryCodePicker Cpp;
    ProgressDialog mDialog;

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth FAuth;

    String role = "LogisticsPerson";
    String statee, cityy, suburban, fname, lname, mobile, confirmpassword, password, Area, Postcode, house, emailid;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics_registeration);

        //TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register As Logistics");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //XML
        Fname = (TextInputLayout) findViewById(R.id.fname);
        Lname = (TextInputLayout) findViewById(R.id.lname);
        Pass = (TextInputLayout) findViewById(R.id.password);
        Email = (TextInputLayout) findViewById(R.id.Emailid);
        cfpass = (TextInputLayout) findViewById(R.id.confirmpassword);
        mobileno = (TextInputLayout) findViewById(R.id.mobileno);
        houseno = (TextInputLayout) findViewById(R.id.Houseno);
        area = (TextInputLayout) findViewById(R.id.Areaa);
        postcode = (TextInputLayout) findViewById(R.id.Postcodee);
        statespin = (Spinner) findViewById(R.id.State);
        Cityspin = (Spinner) findViewById(R.id.City);
        Emaill = (Button) findViewById(R.id.emaillid);
        Suburban = (Spinner) findViewById(R.id.suburban);
        signup = (Button) findViewById(R.id.Signupp);
        Phone = (Button) findViewById(R.id.Phonenumber);
        Cpp = (CountryCodePicker) findViewById(R.id.ctrycode);

        //PROGRESS DIALOG
        final ProgressDialog mDialog = new ProgressDialog(Logistics_registration.this);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);

        //PROVINCE SPIN
        statespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object value = parent.getItemAtPosition(position);
                statee = value.toString().trim();
                ArrayList<String> list = new ArrayList<>();
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Logistics_registration.this, android.R.layout.simple_spinner_item, list);
                switch (statee){
                    case "Davao de Oro":
                        Collections.addAll(list, Davao_de_Oro);
                        Cityspin.setAdapter(arrayAdapter);
                        break;
                    case "Davao del Sur":
                        Collections.addAll(list, Davao_del_Sur);
                        Cityspin.setAdapter(arrayAdapter);
                        break;
                    case "Davao del Norte":
                        Collections.addAll(list, Davao_del_Norte);
                        Cityspin.setAdapter(arrayAdapter);
                        break;
                    case "Davao Oriental":
                        Collections.addAll(list, Davao_Oriental);
                        Cityspin.setAdapter(arrayAdapter);
                        break;
                    case "Davao Occidental":
                        Collections.addAll(list, Davao_Occidental);
                        Cityspin.setAdapter(arrayAdapter);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //CITY SPIN
        Cityspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object value = parent.getItemAtPosition(position);
                cityy = value.toString().trim();
                ArrayList<String> list = new ArrayList<>();
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Logistics_registration.this, android.R.layout.simple_spinner_item, list);
                switch (cityy){
                    //davao de oro
                    case "Compostela":
                        Collections.addAll(list, Compostela);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Laak":
                        Collections.addAll(list, Laak);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Mabini":
                        Collections.addAll(list, Mabini);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Maco":
                        Collections.addAll(list, Maco);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Maragusan":
                        Collections.addAll(list, Maragusan);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Mawab":
                        Collections.addAll(list, Mawab);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Monkayo":
                        Collections.addAll(list, Monkayo);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Montivista":
                        Collections.addAll(list, Montivista);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Nabunturan":
                        Collections.addAll(list, Nabunturan);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "New Bataan":
                        Collections.addAll(list, New_Bataan);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Pantukan":
                        Collections.addAll(list, Pantukan);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    //davao del sur
                    case "Bansalan":
                        Collections.addAll(list, Bansalan);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Davao City":
                        Collections.addAll(list, Davao_City);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Digos":
                        Collections.addAll(list, Digos);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Hagonoy":
                        Collections.addAll(list, Hagonoy);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Kiblawan":
                        Collections.addAll(list, Kiblawan);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Magsaysay":
                        Collections.addAll(list, Magsaysay);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Malalag":
                        Collections.addAll(list, Malalag);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Matanao":
                        Collections.addAll(list, Matanao);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Padada":
                        Collections.addAll(list, Padada);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Santa Cruz":
                        Collections.addAll(list, Santa_Cruz);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Sulop":
                        Collections.addAll(list, Sulop);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    //davao del norte
                    case "Asuncion":
                        Collections.addAll(list, Asuncion);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Baraulio E. Dujali":
                        Collections.addAll(list, Baraulio_E_Dujali);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Carmen":
                        Collections.addAll(list, Carmen);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Kapalong":
                        Collections.addAll(list, Kapalong);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "New Corella":
                        Collections.addAll(list, New_Corella);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Panabo":
                        Collections.addAll(list, Panabo);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Samal":
                        Collections.addAll(list, Samal);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "San Isidro":
                        Collections.addAll(list, San_Isidro);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Santo Tomas":
                        Collections.addAll(list, Santo_Tomas);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Tagum":
                        Collections.addAll(list, Tagum);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Talaingud":
                        Collections.addAll(list, Talaingud);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    //davao oriental
                    case "Baganga":
                        Collections.addAll(list, Baganga);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Banaybanay":
                        Collections.addAll(list, Banaybanay);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Boston":
                        Collections.addAll(list, Boston);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Caraga":
                        Collections.addAll(list, Caraga);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Cateel":
                        Collections.addAll(list, Cateel);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Governor Generoso":
                        Collections.addAll(list, Governor_Generoso);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Lupon":
                        Collections.addAll(list, Lupon);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Manay":
                        Collections.addAll(list, Manay);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Mati":
                        Collections.addAll(list, Mati);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "San Isidro (Oriental)":
                        Collections.addAll(list, San_Isidro_Oriental);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    //davao occidental
                    case "Don Marcelino":
                        Collections.addAll(list, Don_Marcelino);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Jose Abad Santos":
                        Collections.addAll(list, Jose_Abad_Santos);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Malita":
                        Collections.addAll(list, Malita);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Santa Maria":
                        Collections.addAll(list, Santa_Maria);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                    case "Sarangani":
                        Collections.addAll(list, Sarangani);
                        Suburban.setAdapter(arrayAdapter);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //BARANGGAY SPIN
        Suburban.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object value = parent.getItemAtPosition(position);
                suburban = value.toString().trim();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //TODO change the reference name to Logistics DONE
        databaseReference = firebaseDatabase.getInstance().getReference("LogisticsPerson");
        FAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fname = Fname.getEditText().getText().toString().trim();
                lname = Lname.getEditText().getText().toString().trim();
                mobile = mobileno.getEditText().getText().toString().trim();
                emailid = Email.getEditText().getText().toString().trim();
                password = Pass.getEditText().getText().toString().trim();
                confirmpassword = cfpass.getEditText().getText().toString().trim();
                Area = area.getEditText().getText().toString().trim();
                house = houseno.getEditText().getText().toString().trim();
                Postcode = postcode.getEditText().getText().toString().trim();

                phoneNumber = Cpp.getSelectedCountryCodeWithPlus() + mobile;

                if (isValid()) {
                    mDialog.setMessage("Registering please wait...");
                    mDialog.show();

                    FAuth.createUserWithEmailAndPassword(emailid, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String useridd = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                databaseReference = FirebaseDatabase.getInstance().getReference("User").child(useridd);
                                final HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("Role", role);
                                //TODO this is the database for the DeliveryPerson
                                databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {

                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        HashMap<String, String> hashMappp = new HashMap<>();
                                        hashMappp.put("Area", Area);
                                        hashMappp.put("City", cityy);
                                        hashMappp.put("ConfirmPassword", confirmpassword);
                                        hashMappp.put("EmailID", emailid);
                                        hashMappp.put("Fname", fname);
                                        hashMappp.put("House", house);
                                        hashMappp.put("Lname", lname);
                                        hashMappp.put("Mobile", phoneNumber);
                                        hashMappp.put("Password", password);
                                        hashMappp.put("Postcode", Postcode);
                                        hashMappp.put("State", statee);
                                        hashMappp.put("Suburban", suburban);
                                        //Todo change the deliveryperson to Logistics DONE
                                        firebaseDatabase.getInstance().getReference("LogisticsPerson").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(hashMappp).addOnCompleteListener(new OnCompleteListener<Void>() {

                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                FAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            mDialog.dismiss();

                                                            AlertDialog.Builder builder = new AlertDialog.Builder(Logistics_registration.this);
                                                            builder.setMessage("Registered Successfully,Please Verify your Email");
                                                            builder.setCancelable(false);
                                                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    Intent b = new Intent(Logistics_registration.this, Logistics_VerifyPhone.class);
                                                                    b.putExtra("phoneNumber", phoneNumber);
                                                                    startActivity(b);
                                                                }
                                                            });
                                                            AlertDialog alert = builder.create();
                                                            alert.show();

                                                        } else {
                                                            mDialog.dismiss();
                                                            ReusableCodeForAll.ShowAlert(Logistics_registration.this, "Error", task.getException().getMessage());

                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });


                            } else {
                                mDialog.dismiss();
                                ReusableCodeForAll.ShowAlert(Logistics_registration.this, "Error", task.getException().getMessage());
                            }

                        }
                    });


                }
            }
        });

        Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent e = new Intent(Logistics_registration.this, Logistics_LoginPhone.class);
                startActivity(e);
                finish();
            }
        });

        Emaill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Logistics_registration.this, Logistics_Login.class);
                startActivity(a);
                finish();
            }
        });


    }

    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public boolean isValid() {
        Fname.setErrorEnabled(false);
        Fname.setError("");
        Email.setErrorEnabled(false);
        Email.setError("");
        Lname.setErrorEnabled(false);
        Lname.setError("");
        Pass.setErrorEnabled(false);
        Pass.setError("");
        mobileno.setErrorEnabled(false);
        mobileno.setError("");
        cfpass.setErrorEnabled(false);
        cfpass.setError("");
        area.setErrorEnabled(false);
        area.setError("");
        houseno.setErrorEnabled(false);
        houseno.setError("");
        postcode.setErrorEnabled(false);
        postcode.setError("");

        boolean isValidname = false, isvalidpassword = false, isValidemail = false, isvalidconfirmpassword = false, isvalid = false, isvalidmobileno = false, isvalidlname = false, isvalidhousestreetno = false, isvalidarea = false, isvalidpostcode = false;
        if (TextUtils.isEmpty(fname)) {
            Fname.setErrorEnabled(true);
            Fname.setError("Firstname is required");
        } else {
            isValidname = true;
        }
        if (TextUtils.isEmpty(lname)) {
            Lname.setErrorEnabled(true);
            Lname.setError("Lastname is required");
        } else {
            isvalidlname = true;
        }
        if (TextUtils.isEmpty(emailid)) {
            Email.setErrorEnabled(true);
            Email.setError("Email is required");
        } else {
            if (emailid.matches(emailpattern)) {
                isValidemail = true;
            } else {
                Email.setErrorEnabled(true);
                Email.setError("Enter a valid Email Address");
            }

        }

        if (TextUtils.isEmpty(password)) {
            Pass.setErrorEnabled(true);
            Pass.setError("Password is required");
        } else {
            if (password.length() < 6) {
                Pass.setErrorEnabled(true);
                Pass.setError("password too weak");
            } else {
                isvalidpassword = true;
            }
        }
        if (TextUtils.isEmpty(confirmpassword)) {
            cfpass.setErrorEnabled(true);
            cfpass.setError("Confirm Password is required");
        } else {
            if (!password.equals(confirmpassword)) {
                Pass.setErrorEnabled(true);
                Pass.setError("Password doesn't match");
            } else {
                isvalidconfirmpassword = true;
            }
        }
        if (TextUtils.isEmpty(mobile)) {
            mobileno.setErrorEnabled(true);
            mobileno.setError("Mobile number is required");
        } else {
            if (mobile.length() < 10) {
                mobileno.setErrorEnabled(true);
                mobileno.setError("Invalid mobile number");
            } else {
                isvalidmobileno = true;
            }
        }
        if (TextUtils.isEmpty(house)) {
            houseno.setErrorEnabled(true);
            houseno.setError("Field cannot be empty");
        } else {
            isvalidhousestreetno = true;
        }
        if (TextUtils.isEmpty(Area)) {
            area.setErrorEnabled(true);
            area.setError("Field cannot be empty");
        } else {
            isvalidarea = true;
        }
        if (TextUtils.isEmpty(Postcode)) {
            postcode.setErrorEnabled(true);
            postcode.setError("Field cannot be empty");
        } else {
            isvalidpostcode = true;
        }

        isvalid = (isValidname && isvalidpostcode && isValidemail && isvalidlname && isvalidconfirmpassword && isvalidpassword && isvalidmobileno && isvalidarea && isvalidhousestreetno) ? true : false;
        return isvalid;
    }
}
