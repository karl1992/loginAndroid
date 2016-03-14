package xzh.com.listviewhover.model;

/**
 * Created by xiangzhihong 2015/7/24 15:09.
 * Describel
 */

public class CountryDetail {
    public int Id; //国家、州/地区、城市编号id
    public String NameEn;
    public String NameCn;
    public String Code;//国家的手机区号(当GetType为1时此字段才有值)
    public boolean IsState;//是否含有省/州
    public boolean IsCity;//是否含有城市

}
