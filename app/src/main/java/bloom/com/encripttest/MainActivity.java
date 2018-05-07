package bloom.com.encripttest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Arrays;
import java.util.List;

import bloom.com.encryption.Enc;

public class MainActivity extends AppCompatActivity {
    EditText ET_input ;
    Button BTN_Test;
    TextView txt_Encripted,txt_Decripted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ET_input = findViewById(R.id.ET_input);
        BTN_Test = findViewById(R.id.BTN_Test);

        txt_Encripted = findViewById(R.id.txt_Encripted);
        txt_Decripted = findViewById(R.id.txt_Decripted);

        List<String> keys = Arrays.asList(getResources().getStringArray(R.array.EncKeys));
        final Enc enc = new Enc(keys);
        BTN_Test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Encrypted = enc.Encrypt(ET_input.getText().toString());
                txt_Encripted.setText(Encrypted);
                String Decrypted = enc.Decrypt(Encrypted);
                txt_Decripted.setText(Decrypted);
            }
        });

    }
}
