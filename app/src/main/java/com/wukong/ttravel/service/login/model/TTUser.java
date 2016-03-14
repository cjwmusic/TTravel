package com.wukong.ttravel.service.login.model;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by wukong on 3/13/16.
 */
public class TTUser {

    private String userId;
    private String userPhoneNumber;
    private Boolean membVerityM;
    private String email;
    private String password; //MD5
    private String nickName;
    private String userAvatar;
    private String userAlbum;
    private String firstPicture;
    private String userRealName;
    private int gender;
    private String birthDay;
    private String identity;
    private String identityP;
    private String authI;
    private String destination;
    private String address;
    private long longitude;
    private long latitude;
    private String occupation;
    private String hometown;
    private String experience;
    private String lable;
    private String remark;
    private String favorites;
    private Integer pattern;
    private boolean online;
    private String last;
    private String payKey;
    private long balance;
    private long income;
    private long defray;
    private long withDraw;
    private String cashKey;
    private long soon;
    private int status;
    private String createTime;
    private String blackList;
    private boolean stick;
    private int evaluation;
    private long score;
    private int browser;
    private int order;
    private boolean commenWeal;
    private int product;
    private boolean house;
    private boolean car;
    private String deviceToken;
    private String unionId;


    public TTUser() {

    }

    public TTUser(JSONObject jsonObject) {
        userId = jsonObject.getString("MembID");
        userPhoneNumber = jsonObject.getString("MembMobile");
        email = jsonObject.getString("MembEmail");
        userAvatar = jsonObject.getString("MembPhoto");
        nickName = jsonObject.getString("MembNickname");
        userRealName = jsonObject.getString("MembName");
        gender = jsonObject.getInteger("MembSex");
        hometown = jsonObject.getString("MembHometown");
        userRealName = jsonObject.getString("MembName");
        occupation = jsonObject.getString("MembOccupation");

    }

    public String getUserAlbum() {
        return userAlbum;
    }

    public void setUserAlbum(String userAlbum) {
        this.userAlbum = userAlbum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public Boolean getMembVerityM() {
        return membVerityM;
    }

    public void setMembVerityM(Boolean membVerityM) {
        this.membVerityM = membVerityM;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getIdentityP() {
        return identityP;
    }

    public void setIdentityP(String identityP) {
        this.identityP = identityP;
    }

    public String getAuthI() {
        return authI;
    }

    public void setAuthI(String authI) {
        this.authI = authI;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFavorites() {
        return favorites;
    }

    public void setFavorites(String favorites) {
        this.favorites = favorites;
    }

    public Integer getPattern() {
        return pattern;
    }

    public void setPattern(Integer pattern) {
        this.pattern = pattern;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getPayKey() {
        return payKey;
    }

    public void setPayKey(String payKey) {
        this.payKey = payKey;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public long getIncome() {
        return income;
    }

    public void setIncome(long income) {
        this.income = income;
    }

    public long getDefray() {
        return defray;
    }

    public void setDefray(long defray) {
        this.defray = defray;
    }

    public long getWithDraw() {
        return withDraw;
    }

    public void setWithDraw(long withDraw) {
        this.withDraw = withDraw;
    }

    public String getCashKey() {
        return cashKey;
    }

    public void setCashKey(String cashKey) {
        this.cashKey = cashKey;
    }

    public long getSoon() {
        return soon;
    }

    public void setSoon(long soon) {
        this.soon = soon;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getBlackList() {
        return blackList;
    }

    public void setBlackList(String blackList) {
        this.blackList = blackList;
    }

    public boolean isStick() {
        return stick;
    }

    public void setStick(boolean stick) {
        this.stick = stick;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public int getBrowser() {
        return browser;
    }

    public void setBrowser(int browser) {
        this.browser = browser;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isCommenWeal() {
        return commenWeal;
    }

    public void setCommenWeal(boolean commenWeal) {
        this.commenWeal = commenWeal;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public boolean isHouse() {
        return house;
    }

    public void setHouse(boolean house) {
        this.house = house;
    }

    public boolean isCar() {
        return car;
    }

    public void setCar(boolean car) {
        this.car = car;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }
}
