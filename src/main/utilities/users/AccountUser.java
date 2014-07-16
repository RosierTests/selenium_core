package users;

import utilities.users.User;

/**
 * Created By: bsmith on 2/26/14.
 * Package: users
 * Description: Create a personal user extending the base user class with additional account information.
 */
public class AccountUser extends User {
    private String[] accountUserInfo;

    /**
     * The constructor sets all personal user account data into a field array of strings.
     * @param firstName first name
     * @param lastName last name
     * @param email email address
     * @param userName user name
     * @param password password
     * @param sex sex
     * @param birthDate birth date
     * @param address1 primary address
     * @param address2 secondary address
     * @param state state
     * @param city city
     * @param zipCode zip code
     * @param country country
     * @param phone primary phone number
     * @param mobilePhone mobile phone number
     */
    public AccountUser(String firstName, String lastName, String email, String userName, String password,
                       String sex, String birthDate, String address1, String address2, String state, String city,
                       String zipCode, String country, String phone, String mobilePhone) {
        super(firstName, lastName, email, userName, password);
        this.accountUserInfo = new String[] {sex, birthDate, address1, address2, state, city, zipCode,
                country, phone, mobilePhone};
    }

    /**
     * Get sex.
     * @return user's sex
     */
    public String getSex() {
        return this.accountUserInfo[5];
    }

    /**
     * Set sex.
     * @param sex user's sex
     */
    public void setSex(String sex) {
        this.accountUserInfo[5] = sex;
    }

    /**
     * Get birth date.
     * @return user's birth date
     */
    public String getBirthDate() {
        return this.accountUserInfo[6];
    }

    /**
     * Set birth date.
     * @param birthDate user's birth date
     */
    public void setBirthDate(String birthDate) {
        this.accountUserInfo[6] = birthDate;
    }

    /**
     * Get primary address.
     * @return user's primary address
     */
    public String getPrimaryAddress() {
        return this.accountUserInfo[7];
    }

    /**
     * Set primary address.
     * @param primaryAddress user's primary address
     */
    public void setPrimaryAddress(String primaryAddress) {
        this.accountUserInfo[7] = primaryAddress;
    }

    /**
     * Get secondary address.
     * @return user's secondary address
     */
    public String getSecondaryAddress() {
        return this.accountUserInfo[8];
    }

    /**
     * Set secondary address.
     * @param secondaryAddress user's secondary address
     */
    public void setSecondaryAddress(String secondaryAddress) {
        this.accountUserInfo[8] = secondaryAddress;
    }

    /**
     * Get state.
     * @return user's state
     */
    public String getState() {
        return this.accountUserInfo[9];
    }

    /**
     * Set state.
     * @param state user's state
     */
    public void setState(String state) {
        this.accountUserInfo[9] = state;
    }

    /**
     * Get city.
     * @return user's city
     */
    public String getCity() {
        return this.accountUserInfo[10];
    }

    /**
     * Set city.
     * @param city user's city
     */
    public void setCity(String city) {
        this.accountUserInfo[10] = city;
    }

    /**
     * Get zip code.
     * @return user's zip code
     */
    public String getZipCode() {
        return this.accountUserInfo[11];
    }

    /**
     * Set zip code.
     * @param zipCode user's zipCode
     */
    public void setZipCode(String zipCode) {
        this.accountUserInfo[11] = zipCode;
    }

    /**
     * Get country.
     * @return user's country
     */
    public String getCountry() {
        return this.accountUserInfo[12];
    }

    /**
     * Set country.
     * @param country user's country
     */
    public void setCountry(String country) {
        this.accountUserInfo[12] = country;
    }

    /**
     * Get phone number.
     * @return user's phone number
     */
    public String getPhone() {
        return this.accountUserInfo[13];
    }

    /**
     * Set phone number.
     * @param phone user's phone number
     */
    public void setPhone(String phone) {
        this.accountUserInfo[13] = phone;
    }

    /**
     * Get mobile phone number.
     * @return user's mobile phone number
     */
    public String getMobilePhone() {
        return this.accountUserInfo[14];
    }

    /**
     * Set mobile phone number.
     * @param mobilePhone user's mobile phone number
     */
    public void setMobilePhone(String mobilePhone) {
        this.accountUserInfo[14] = mobilePhone;
    }
}
