package com.example.learnmaps.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.learnmaps.R;
import com.example.learnmaps.adapters.DetailRecyclerAdapter;
import com.example.learnmaps.adapters.PedagangRecyclerAdapter;
import com.example.learnmaps.models.Detail;
import com.example.learnmaps.models.Kategori;
import com.example.learnmaps.models.UserLocationPedagang;
import com.example.learnmaps.models.UserPedagang;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DetailActivity extends AppCompatActivity implements DetailRecyclerAdapter.DetailListRecyclerClickListener {

    private static final String TAG = "DetailActivity";

    //widgets
    private UserPedagang mUserList;
    //private EditText mMessage;

    //vars
    private ListenerRegistration mPedagangeEventListener, mUserListEventListener;
    private RecyclerView mDetailRecyclerView;
    private DetailRecyclerAdapter mDetailRecyclerAdapter;
    private FirebaseFirestore mDb;
    //private ArrayList<ChatMessage> mMessages = new ArrayList<>();
    private Set<String> mPedagangIds = new HashSet<>();
    private ArrayList<Detail> mDetail = new ArrayList<>();
    private ArrayList<UserLocationPedagang> mUserLocations = new ArrayList<>();
    private DocumentSnapshot mLastQueriedDocument;
    private Button buttonWhatsapp;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //mMessage = findViewById(R.id.input_message);
        mDetailRecyclerView = findViewById(R.id.detail_list_recycler_view);
        buttonWhatsapp = (Button) findViewById(R.id.buttonWa);

        //findViewById(R.id.checkmark).setOnClickListener(this);

        mDb = FirebaseFirestore.getInstance();

        //initSupportActionBar();
        getIncomingIntent();

        //getChatroomUsers();
        //getPedagangs();
    }

    //private void initSupportActionBar(){
        //setTitle("List Pedagang");
    //}

    private void getDetail(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference pedagangCollectionRef = db
                .collection("Detail Pedagang");

        Query pedagangQuery = null;
        if(mLastQueriedDocument != null){
            pedagangQuery = pedagangCollectionRef
                    .whereEqualTo("user_id", mUserList.getUser_id())
                    .startAfter(mLastQueriedDocument);
        }
        else{
            pedagangQuery = pedagangCollectionRef
                    .whereEqualTo("user_id", mUserList.getUser_id());
        }

        pedagangQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){

                    for(QueryDocumentSnapshot document: task.getResult()){
                        Detail detail = document.toObject(Detail.class);
                        mDetail.add(detail);
//                        Log.d(TAG, "onComplete: got a new note. Position: " + (mNotes.size() - 1));
                    }

                    if(task.getResult().size() != 0){
                        mLastQueriedDocument = task.getResult().getDocuments()
                                .get(task.getResult().size() -1);
                    }

                    mDetailRecyclerAdapter.notifyDataSetChanged();
                }
                else{
                    //makeSnackBarMessage("Query Failed. Check Logs.");
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "Query Failed. Check Logs.", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initChatroomRecyclerView(){

        mDetailRecyclerAdapter = new DetailRecyclerAdapter(mDetail,this);
        mDetailRecyclerView.setAdapter(mDetailRecyclerAdapter);
        mDetailRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


//    private void inflateUserListFragment(){
//        hideSoftKeyboard();
//
//        PedagangListFragment fragment = PedagangListFragment.newInstance();
//        Bundle bundle = new Bundle();
//        bundle.putParcelableArrayList(getString(R.string.intent_pedagang_list), mUserList);
//        bundle.putParcelableArrayList(getString(R.string.intent_user_locations_pedagang), mUserLocations);
//        fragment.setArguments(bundle);
//
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
//        transaction.replace(R.id.pedagang_list_container, fragment, getString(R.string.fragment_pedagang_list));
//        transaction.addToBackStack(getString(R.string.fragment_pedagang_list));
//        transaction.commit();
//    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra(getString(R.string.intent_detail))){
            mUserList = getIntent().getParcelableExtra(getString(R.string.intent_detail));
            setChatroomName();
            //joinChatroom();

            //getDetail();
        }
        if(getIntent().hasExtra(getString(R.string.intent_detail2))){
            mUserList = getIntent().getParcelableExtra(getString(R.string.intent_detail2));
            //setChatroomName();
            //joinChatroom();
            initChatroomRecyclerView();
            getDetail();
        }
    }

    private void setChatroomName(){
        getSupportActionBar().setTitle(mUserList.getUsername());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //getChatMessages();
        //getPedagangs();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPedagangeEventListener != null){
            mPedagangeEventListener.remove();
        }
        if(mUserListEventListener != null){
            mUserListEventListener.remove();
        }
    }

    @Override
    public void onPedagangClicked(int position) {

    }
}