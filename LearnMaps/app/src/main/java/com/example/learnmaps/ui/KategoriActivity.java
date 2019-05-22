package com.example.learnmaps.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.learnmaps.R;
import com.example.learnmaps.UserClient;
import com.example.learnmaps.adapters.PedagangRecyclerAdapter;
import com.example.learnmaps.adapters.KategoriRecyclerAdapter;
import com.example.learnmaps.adapters.PedagangRecyclerAdapter;
//import com.example.learnmaps.models.ChatMessage;
import com.example.learnmaps.models.Kategori;
import com.example.learnmaps.models.Kategori;
import com.example.learnmaps.models.UserPedagang;
import com.example.learnmaps.models.UserLocationPedagang;
import com.example.learnmaps.models.UserLocationPedagang;
import com.example.learnmaps.models.UserPedagang;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class KategoriActivity extends AppCompatActivity implements
        View.OnClickListener, PedagangRecyclerAdapter.PedagangListRecyclerClickListener {

    private static final String TAG = "KategoriActivity";

    //widgets
    private Kategori mKategori;
    //private EditText mMessage;

    //vars
    private ListenerRegistration mPedagangeEventListener, mUserListEventListener;
    private RecyclerView mPedagangRecyclerView;
    private PedagangRecyclerAdapter mPedagangRecyclerAdapter;
    private FirebaseFirestore mDb;
    //private ArrayList<ChatMessage> mMessages = new ArrayList<>();
    private Set<String> mPedagangIds = new HashSet<>();
    private ArrayList<UserPedagang> mUserList = new ArrayList<>();
    private ArrayList<UserLocationPedagang> mUserLocations = new ArrayList<>();
    private DocumentSnapshot mLastQueriedDocument;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);

        //mMessage = findViewById(R.id.input_message);
        mPedagangRecyclerView = findViewById(R.id.pedagang_list_recycler_view);

        //findViewById(R.id.checkmark).setOnClickListener(this);

        mDb = FirebaseFirestore.getInstance();

        //initSupportActionBar();
        getIncomingIntent();
        initChatroomRecyclerView();
        getIncomingIntent2();

    }

    private void initSupportActionBar(){
        setTitle("List Pedagang");
    }

    private void getUserLocation(UserPedagang userPedagang){
        DocumentReference locationsRef = mDb
                .collection(getString(R.string.collection_user_locations_pedagang))
                .document(userPedagang.getUser_id());

        locationsRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){
                    if(task.getResult().toObject(UserLocationPedagang.class) != null){

                        mUserLocations.add(task.getResult().toObject(UserLocationPedagang.class));
                    }
                }
            }
        });

    }

//    private void getPedagangs(){
//
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//        CollectionReference pedagangCollectionRef = db
//                .collection("Users Pedagang");
//
//        Query pedagangQuery = null;
//        if(mLastQueriedDocument != null){
//            pedagangQuery = pedagangCollectionRef
//                    .whereEqualTo("kategori_id", mKategori.getKategori_id())
//                    .startAfter(mLastQueriedDocument);
//        }
//        else{
//            pedagangQuery = pedagangCollectionRef
//                    .whereEqualTo("kategori_id", mKategori.getKategori_id());
//        }
//
//        pedagangQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()){
//
//                    for(QueryDocumentSnapshot document: task.getResult()){
//                        UserPedagang userPedagang = document.toObject(UserPedagang.class);
//                        mUserList.add(userPedagang);
////                        Log.d(TAG, "onComplete: got a new note. Position: " + (mNotes.size() - 1));
//                    }
//
//                    if(task.getResult().size() != 0){
//                        mLastQueriedDocument = task.getResult().getDocuments()
//                                .get(task.getResult().size() -1);
//                    }
//
//                    mPedagangRecyclerAdapter.notifyDataSetChanged();
//                }
//                else{
//                    //makeSnackBarMessage("Query Failed. Check Logs.");
//                    View parentLayout = findViewById(android.R.id.content);
//                    Snackbar.make(parentLayout, "Query Failed. Check Logs.", Snackbar.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }


//    private void getPedagangs(){
//
//        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
//                .setTimestampsInSnapshotsEnabled(true)
//                .build();
//        mDb.setFirestoreSettings(settings);
//
//        CollectionReference messagesRef = mDb
//                //.collection(getString(R.string.collection_kategori))
//                //.document(mKategori.getKategori_id())
//                //.collection(getString(R.string.collection_kategori_user_list));
//                .collection(getString(R.string.collection_pedagang));
//        mPedagangeEventListener = messagesRef
//                .whereEqualTo("kategori_id", "cdjV2r01KJXpgrQUmjD0")
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
//                Log.d(TAG, "onEvent: called.");
//
//                if (e != null) {
//                    Log.e(TAG, "onEvent: Listen failed.", e);
//                    return;
//                }
//
//                if(queryDocumentSnapshots != null){
//                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
//
//                        UserPedagang userPedagang = doc.toObject(UserPedagang.class);
//                        //if(!mPedagangIds.contains(userPedagang.getUser_id())){
//                            //mPedagangIds.add(userPedagang.getUser_id());
//                            mUserList.add(userPedagang);
//                        //}
//                    }
//                    Log.d(TAG, "onEvent: number of kategoris: " + mUserList.size());
//                    mPedagangRecyclerAdapter.notifyDataSetChanged();
//                }
//                else {
//                    View parentLayout = findViewById(android.R.id.content);
//                    Snackbar.make(parentLayout, "Something went wrong.", Snackbar.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//    }

//    private void getChatMessages(){
//
//        CollectionReference messagesRef = mDb
//                .collection(getString(R.string.collection_kategori))
//                .document(mKategori.getKategori_id())
//                .collection(getString(R.string.collection_kategori_user_list));
//
//        mChatMessageEventListener = messagesRef
//                //.orderBy("timestamp", Query.Direction.ASCENDING)
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
//                        if (e != null) {
//                            Log.e(TAG, "onEvent: Listen failed.", e);
//                            return;
//                        }
//
//                        if(queryDocumentSnapshots != null){
////                            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
////
////                                ChatMessage message = doc.toObject(ChatMessage.class);
////                                if(!mMessageIds.contains(message.getMessage_id())){
////                                    mMessageIds.add(message.getMessage_id());
////                                    mMessages.add(message);
////                                    mChatMessageRecyclerView.smoothScrollToPosition(mMessages.size() - 1);
////                                }
////
////                            }
//                            mPedagangRecyclerAdapter.notifyDataSetChanged();
//
//                        }
//                    }
//                });
//    }

    private void getPedagangs(){

        CollectionReference usersRef = mDb
                .collection("Kategori")
                .document(mKategori.getKategori_id())
                .collection("Pedagang List");

        usersRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){

                    for(QueryDocumentSnapshot document: task.getResult()){
                        UserPedagang userPedagang = document.toObject(UserPedagang.class);
                        mUserList.add(userPedagang);
//                        Log.d(TAG, "onComplete: got a new note. Position: " + (mNotes.size() - 1));
                    }

                    if(task.getResult().size() != 0){
                        mLastQueriedDocument = task.getResult().getDocuments()
                                .get(task.getResult().size() -1);
                    }

                    mPedagangRecyclerAdapter.notifyDataSetChanged();
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

        mPedagangRecyclerAdapter = new PedagangRecyclerAdapter(mUserList,this);
        mPedagangRecyclerView.setAdapter(mPedagangRecyclerAdapter);
        mPedagangRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//        mPedagangRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View v,
//                                       int left, int top, int right, int bottom,
//                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                if (bottom < oldBottom) {
//                    mPedagangRecyclerView.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
////                            if(mMessages.size() > 0){
////                                mPedagangRecyclerView.smoothScrollToPosition(
////                                        mPedagangRecyclerView.getAdapter().getItemCount() - 1);
////                            }
//
//                        }
//                    }, 100);
//                }
//            }
//        });

    }


//    private void insertNewMessage(){
//        String message = mMessage.getText().toString();
//
//        if(!message.equals("")){
//            message = message.replaceAll(System.getProperty("line.separator"), "");
//
//            DocumentReference newMessageDoc = mDb
//                    .collection(getString(R.string.collection_chatrooms))
//                    .document(mKategori.getChatroom_id())
//                    .collection(getString(R.string.collection_chat_messages))
//                    .document();
//
//            ChatMessage newChatMessage = new ChatMessage();
//            newChatMessage.setMessage(message);
//            newChatMessage.setMessage_id(newMessageDoc.getId());
//
//            UserPedagang userPedagang = ((UserClient)(getApplicationContext())).getUser();
//            Log.d(TAG, "insertNewMessage: retrieved userPedagang client: " + userPedagang.toString());
//            newChatMessage.setUser(userPedagang);
//
//            newMessageDoc.set(newChatMessage).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    if(task.isSuccessful()){
//                        clearMessage();
//                    }else{
//                        View parentLayout = findViewById(android.R.id.content);
//                        Snackbar.make(parentLayout, "Something went wrong.", Snackbar.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
//    }

//    private void clearMessage(){
//        mMessage.setText("");
//    }

    private void inflateUserListFragment(){
        hideSoftKeyboard();

        PedagangListFragment fragment = PedagangListFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(getString(R.string.intent_pedagang_list), mUserList);
        bundle.putParcelableArrayList(getString(R.string.intent_user_locations_pedagang), mUserLocations);
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
        transaction.replace(R.id.pedagang_list_container, fragment, getString(R.string.fragment_pedagang_list));
        transaction.addToBackStack(getString(R.string.fragment_pedagang_list));
        transaction.commit();
    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra(getString(R.string.intent_kategori))){
            mKategori = getIntent().getParcelableExtra(getString(R.string.intent_kategori));
            setChatroomName();
            //joinChatroom();
            //getPedagangs();
            //initChatroomRecyclerView();
        }
    }
    private void getIncomingIntent2(){
        if(getIntent().hasExtra(getString(R.string.intent_listpdg))){
            mKategori = getIntent().getParcelableExtra(getString(R.string.intent_listpdg));
            getPedagangs();
        }
    }

//    private void leaveChatroom(){
//
//        DocumentReference joinChatroomRef = mDb
//                .collection(getString(R.string.collection_chatrooms))
//                .document(mKategori.getChatroom_id())
//                .collection(getString(R.string.collection_chatroom_user_list))
//                .document(FirebaseAuth.getInstance().getUid());
//
//        joinChatroomRef.delete();
//    }

//    private void joinChatroom(){
//
//        DocumentReference joinChatroomRef = mDb
//                .collection(getString(R.string.collection_chatrooms))
//                .document(mKategori.getChatroom_id())
//                .collection(getString(R.string.collection_chatroom_user_list))
//                .document(FirebaseAuth.getInstance().getUid());
//
//        UserPedagang userPedagang = ((UserClient)(getApplicationContext())).getUser();
//        joinChatroomRef.set(userPedagang); // Don't care about listening for completion.
//    }

    private void setChatroomName(){
        getSupportActionBar().setTitle(mKategori.getTitle());
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chatroom_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:{
                PedagangListFragment fragment =
                        (PedagangListFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.fragment_pedagang_list));
                if(fragment != null){
                    if(fragment.isVisible()){
                        getSupportFragmentManager().popBackStack();
                        return true;
                    }
                }
                finish();
                return true;
            }
            case R.id.action_chatroom_user_list:{
                inflateUserListFragment();
                return true;
            }
            case R.id.action_chatroom_leave:{
                //leaveChatroom();
                return true;
            }
            default:{
                return super.onOptionsItemSelected(item);
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.checkmark:{
                //insertNewMessage();
            }
        }
    }

    private void navDetailActivity(UserPedagang userPedagang){
        Intent intent = new Intent(KategoriActivity.this, DetailActivity.class);
        intent.putExtra(getString(R.string.intent_detail), userPedagang);
        intent.putExtra(getString(R.string.intent_detail2), userPedagang);
        startActivity(intent);
    }

    @Override
    public void onUserPedagangClicked(int position) {
        navDetailActivity(mUserList.get(position));
    }
}