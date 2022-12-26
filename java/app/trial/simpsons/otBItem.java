package app.trial.simpsons;

public class otBItem {

    private String mid, mrid, mstatus, mitemno, mmaterial, mdescription, mquantity, muom, mwno, mbatch, mbin, msled;

    public otBItem(String id, String rid, String status, String itemno, String material, String description, String quantity, String uom, String wno, String batch, String bin, String sled) {

        mid = id;
        mrid = rid;

        mitemno = itemno;
        mmaterial = material;
        mdescription = description;
        mquantity = quantity;
        muom = uom;
        mwno = wno;
        mbatch = batch;
        mbin = bin;
        msled = sled;
        mstatus = status;


    }

    public String getid() {
        return mid;
    }

    public String getrid() {
        return mrid;
    }

    public String getstatus() {
        return mstatus;
    }

    public String getitemno() {
        return mitemno;
    }

    public String getmaterial() {
        return mmaterial;
    }

    public String getdescription() {
        return mdescription;
    }

    public String getquantity() {
        return mquantity;
    }

    public String getuom() {
        return muom;
    }

    public String getwno() {
        return mwno;
    }

    public String getbatch() {
        return mbatch;
    }

    public String getbin() {
        return mbin;
    }

    public String getsled() {
        return msled;
    }


}
