package koti.fcmdbreadwrite;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements ValueEventListener{
    private TextView HeadingText;
    private EditText HeadingInput;
    private RadioButton RbRed,RbBlue;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference = firebaseDatabase.getReference();
    private DatabaseReference mHeadingReference = mRootReference.child("Heading");
    private DatabaseReference mFontColorReference = mRootReference.child("Font Color");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HeadingText = (TextView)findViewById(R.id.headingtext);
        HeadingInput = (EditText)findViewById(R.id.headingInput);
        RbRed = (RadioButton)findViewById(R.id.rbRed);
        RbBlue = (RadioButton)findViewById(R.id.brBlue);
    }

    public void SubmitHeading(View view)
    {
        String heading = HeadingInput.getText().toString();
        mHeadingReference.setValue(heading);
        HeadingInput.setText("");

    }
    public  void onRadioButtonClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.rbRed:
                      mFontColorReference.setValue("Red");
                      break;
            case R.id.brBlue:
                mFontColorReference.setValue("Blue");
                break;
        }

    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.getValue(String.class)!=null)
        {
            String key = dataSnapshot.getKey();

            if (key.equals("Heading"))
            {
                String heading = dataSnapshot.getValue(String.class);
                HeadingText.setText(heading);
            }
            else if (key.equals("Font Color"))
            {
                String color = dataSnapshot.getValue(String.class);

                if (color.equals("Red"))
                {
                    HeadingText.setTextColor(ContextCompat.getColor(this,R.color.colorRed));
                    RbRed.setChecked(true);
                }
                else  if (color.equals("Blue"))
                {
                    HeadingText.setTextColor(ContextCompat.getColor(this,R.color.colorBlue));
                    RbBlue.setChecked(true);
                }
            }
        }

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        mHeadingReference.addValueEventListener(this);
        mFontColorReference.addValueEventListener(this);
    }
}
