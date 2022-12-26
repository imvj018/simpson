package app.trial.simpsons;

public class ExampleobItem {
    private String mcid, mrid, mdorderno, mcreatedate, mRefer, mchangedate, mVal;

    public ExampleobItem(String cid, String rid, String dorderno, String createdate, String refer, String changedate,  String val) {

        mcid = cid;
        mrid = rid;
        mdorderno = dorderno;
        mcreatedate = createdate;
        mRefer = refer;
        mchangedate = changedate;
        mVal = val;


    }


    public String getcid() {
        return mcid;
    }

    public String getrid() {
        return mrid;
    }

    public String getdorderno() {
        return mdorderno;
    }

    public String getcreatedate() {
        return mcreatedate;
    }

    public String getrefer() {
        return mRefer;
    }
    public String getchangedate() {
        return mchangedate;
    }
    public String getval() {
        return mVal;
    }
}
