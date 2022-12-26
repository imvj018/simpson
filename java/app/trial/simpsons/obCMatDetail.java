package app.trial.simpsons;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static app.trial.simpsons.otoMaterial.EXTRA_ID;
import static app.trial.simpsons.otoMaterial.EXTRA_RID;
import static app.trial.simpsons.otoMaterial.EXTRA_ITEMNO;
import static app.trial.simpsons.otoMaterial.EXTRA_QUANTITY;
import static app.trial.simpsons.otoMaterial.EXTRA_BATCH;
import static app.trial.simpsons.otoMaterial.EXTRA_BIN;
import static app.trial.simpsons.otoMaterial.EXTRA_WNO;
import static app.trial.simpsons.otoMaterial.EXTRA_SLED;
import static app.trial.simpsons.otoMaterial.EXTRA_STATUS;
import static app.trial.simpsons.otoMaterial.EXTRA_UOM;
import static app.trial.simpsons.otoMaterial.EXTRA_DESCRIPTION;
import static app.trial.simpsons.otoMaterial.EXTRA_MATERIAL;


public class obCMatDetail extends AppCompatActivity implements View.OnClickListener {

    TextView matnum, matdesc, bin, qty;
    Button qrcode;
    String Id, Rid, Itemno, Material, Batch, Bin, Quantity, Wno, Sled, Status, Uom, Description;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint({"CutPasteId", "WrongViewCast", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ob_mat_detail);
        Intent intent = getIntent();
        Id = intent.getStringExtra(EXTRA_ID);
        Rid = intent.getStringExtra(EXTRA_RID);
        Quantity = intent.getStringExtra(EXTRA_QUANTITY);
        Itemno = intent.getStringExtra(EXTRA_ITEMNO);
        Material = intent.getStringExtra(EXTRA_MATERIAL);
        Description = intent.getStringExtra(EXTRA_DESCRIPTION);
        Wno = intent.getStringExtra(EXTRA_WNO);
        Batch = intent.getStringExtra(EXTRA_BATCH);
        Sled = intent.getStringExtra(EXTRA_SLED);
        Uom = intent.getStringExtra(EXTRA_UOM);
        Status = intent.getStringExtra(EXTRA_STATUS);
        Bin = intent.getStringExtra(EXTRA_BIN);

        matnum = findViewById(R.id.matnum);
        matdesc = findViewById(R.id.matdes);
        bin = findViewById(R.id.stbin);
        qty = findViewById(R.id.qty5);

        matnum.setText("Item no " + Itemno + " : " + Material);
        matdesc.setText("Name : " + Description);
        bin.setText("Storage Bin : " + Bin);
        qty.setText("Quantity : " + Quantity + " " + Uom);

        qrcode = findViewById(R.id.button6);
        qrcode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        scancode();
    }

    public void scancode() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning Bin");
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Intent intent1 = getIntent();
        String Bincheck = intent1.getStringExtra(EXTRA_BIN);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                if (result.getContents().equals(Bincheck)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(result.getContents());
                    builder.setTitle("Bin Search");
                    builder.setPositiveButton("Scan Material", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(obCMatDetail.this, obCMatscanDetail.class);
                            intent.putExtra("ID", Id);
                            intent.putExtra("WNO", Wno);
                            intent.putExtra("RID", Rid);
                            intent.putExtra("ITEMNO", Itemno);
                            intent.putExtra("MATERIAL", Material);
                            intent.putExtra("DESCRIPTION", Description);
                            intent.putExtra("BATCH", Batch);
                            intent.putExtra("QUANTITY", Quantity);
                            intent.putExtra("GRUOM", Uom);
                            intent.putExtra("BIN", Bin);
                            intent.putExtra("SLED", Sled);
                            intent.putExtra("STATUS", Status);

                            startActivity(intent);
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();


                } else {
                    Toast.makeText(this, "Bin Mismatch", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "No Results", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}