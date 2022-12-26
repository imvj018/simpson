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

import static app.trial.simpsons.itoMaterial.EXTRA_ID;
import static app.trial.simpsons.itoMaterial.EXTRA_WNUMBER;
import static app.trial.simpsons.itoMaterial.EXTRA_TONO;
import static app.trial.simpsons.itoMaterial.EXTRA_ITEMNO;
import static app.trial.simpsons.itoMaterial.EXTRA_MATERIAL;
import static app.trial.simpsons.itoMaterial.EXTRA_MATDES;
import static app.trial.simpsons.itoMaterial.EXTRA_STLOC;
import static app.trial.simpsons.itoMaterial.EXTRA_BATCH;
import static app.trial.simpsons.itoMaterial.EXTRA_GRQTY;
import static app.trial.simpsons.itoMaterial.EXTRA_GRUOM;
import static app.trial.simpsons.itoMaterial.EXTRA_PERPLT;
import static app.trial.simpsons.itoMaterial.EXTRA_PERUOM;
import static app.trial.simpsons.itoMaterial.EXTRA_BINCAP;
import static app.trial.simpsons.itoMaterial.EXTRA_STKBIN;
import static app.trial.simpsons.itoMaterial.EXTRA_AVAILSTOCK;
import static app.trial.simpsons.itoMaterial.EXTRA_AVAILUOM;
import static app.trial.simpsons.itoMaterial.EXTRA_OBQTY;
import static app.trial.simpsons.itoMaterial.EXTRA_OBUOM;
import static app.trial.simpsons.itoMaterial.EXTRA_EXTSTK;
import static app.trial.simpsons.itoMaterial.EXTRA_SLD;
import static app.trial.simpsons.itoMaterial.EXTRA_STATUS;
import static app.trial.simpsons.itoMaterial.EXTRA_CONVQT;
import static app.trial.simpsons.itoMaterial.EXTRA_EXCESS;

public class MatCDetail extends AppCompatActivity implements View.OnClickListener {
    TextView matnum, matdesc, bin, qty;
    Button qrcode;
    String Id, Wnumber, Tono, Itemno, Material, Matdes, Location, Batch, Qty, Uom, Perplt,
            peruom, Bincap, Bin, Availstock, Availuom, OBqty, OBuom, Exstk, Sldate, Status, Convqt, Excess;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint({"CutPasteId", "WrongViewCast", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mat_detail);
        Intent intent = getIntent();
        Id = intent.getStringExtra(EXTRA_ID);
        Wnumber = intent.getStringExtra(EXTRA_WNUMBER);
        Tono = intent.getStringExtra(EXTRA_TONO);
        Itemno = intent.getStringExtra(EXTRA_ITEMNO);
        Material = intent.getStringExtra(EXTRA_MATERIAL);
        Matdes = intent.getStringExtra(EXTRA_MATDES);
        Location = intent.getStringExtra(EXTRA_STLOC);
        Batch = intent.getStringExtra(EXTRA_BATCH);
        Qty = intent.getStringExtra(EXTRA_GRQTY);
        Uom = intent.getStringExtra(EXTRA_GRUOM);
        Perplt = intent.getStringExtra(EXTRA_PERPLT);
        peruom = intent.getStringExtra(EXTRA_PERUOM);
        Bincap = intent.getStringExtra(EXTRA_BINCAP);
        Bin = intent.getStringExtra(EXTRA_STKBIN);
        Availstock = intent.getStringExtra(EXTRA_AVAILSTOCK);
        Availuom = intent.getStringExtra(EXTRA_AVAILUOM);
        OBqty = intent.getStringExtra(EXTRA_OBQTY);
        OBuom = intent.getStringExtra(EXTRA_OBUOM);
        Exstk = intent.getStringExtra(EXTRA_EXTSTK);
        Sldate = intent.getStringExtra(EXTRA_SLD);
        Status = intent.getStringExtra(EXTRA_STATUS);
        Convqt = intent.getStringExtra(EXTRA_CONVQT);
        Excess = intent.getStringExtra(EXTRA_EXCESS);


        matnum = findViewById(R.id.matnum);
        matdesc = findViewById(R.id.matdes);
        bin = findViewById(R.id.stbin);
        qty = findViewById(R.id.qty5);

        matnum.setText("Item no " + Itemno + " : " + Material);
        matdesc.setText("Name : " + Matdes);
        bin.setText("Storage Bin : " + Bin);
        qty.setText("Quantity : " + Qty + " " + Uom);

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
        String Bincheck = intent1.getStringExtra(EXTRA_STKBIN);
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
                            Intent intent = new Intent(MatCDetail.this, MatscanDetail.class);
                            intent.putExtra("ID", Id);
                            intent.putExtra("WNUMBER", Wnumber);
                            intent.putExtra("TONO", Tono);
                            intent.putExtra("ITEMNO", Itemno);
                            intent.putExtra("MATERIAL", Material);
                            intent.putExtra("MATDES", Matdes);
                            intent.putExtra("STLOC", Location);
                            intent.putExtra("BATCH", Batch);
                            intent.putExtra("GRQTY", Qty);
                            intent.putExtra("GRUOM", Uom);
                            intent.putExtra("PERPLT", Perplt);
                            intent.putExtra("PERUOM", peruom);
                            intent.putExtra("BINCAP", Bincap);
                            intent.putExtra("STKBIN", Bin);
                            intent.putExtra("AVAILSTOCK", Availstock);
                            intent.putExtra("AVAILUOM", Availuom);
                            intent.putExtra("OBQTY", OBqty);
                            intent.putExtra("OBUOM", OBuom);
                            intent.putExtra("EXTSTK", Exstk);
                            intent.putExtra("SLD", Sldate);
                            intent.putExtra("STATUS", Status);
                            intent.putExtra("CONVQT", Convqt);
                            intent.putExtra("EXCESS", Excess);
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