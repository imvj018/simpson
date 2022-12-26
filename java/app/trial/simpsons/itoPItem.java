package app.trial.simpsons;

public class itoPItem {

    private String mid , mwnumber , mtono , mitemno , mmaterial , mmatdesc , mstloc , mbatch , mgrqty , mgruom , mperplt , mperuom , mbincap , mstkbin , mavailstock , mavailuom , mobqty , mobuom , mextstk , msld , mstatus , mconvqt , mexcess;

    public itoPItem(String id, String wnumber, String tono, String itemno, String material, String matdesc, String stloc, String batch, String grqty, String gruom, String perplt, String peruom, String bincap, String stkbin, String availstock, String availuom, String obqty, String obuom, String extstk, String sld, String status, String convqt, String excess) {

        mid = id;
        mwnumber = wnumber;
        mtono = tono;
        mitemno = itemno;
        mmaterial = material;
        mmatdesc = matdesc;
        mstloc = stloc;
        mbatch = batch;
        mgrqty = grqty;
        mgruom = gruom;
        mperplt = perplt;
        mperuom = peruom;
        mbincap = bincap;
        mstkbin = stkbin;
        mavailstock = availstock;
        mavailuom = availuom;
        mobqty = obqty;
        mobuom = obuom;
        mextstk = extstk;
        msld = sld;
        mstatus = status;
        mconvqt = convqt;
        mexcess = excess;



    }

    public  String getid() { return mid;}
    public  String getwnumber() { return mwnumber;}
    public  String gettono() { return mtono;}
    public  String getitemno() { return mitemno;}
    public  String getmaterial() { return mmaterial;}
    public  String getmatdes() { return mmatdesc;}
    public  String getstloc() { return mstloc;}
    public  String getbatch() { return mbatch;}
    public  String getgrqty() { return mgrqty;}
    public  String getgruom() { return mgruom;}
    public  String getperplt() { return mperplt;}
    public  String getperuom() { return mperuom;}
    public  String getbincap() { return mbincap;}
    public  String getstkbin() { return mstkbin;}
    public  String getavailstock() { return mavailstock;}
    public  String getavailuom() { return mavailuom;}
    public  String getobqty() { return mobqty;}
    public  String getobuom() { return mobuom;}
    public  String getextstk() { return mextstk;}
    public  String getsld() { return msld;}
    public  String getstatus() { return mstatus;}
    public  String getconvqt() { return mconvqt;}
    public  String getexcess() { return mexcess;}


}
