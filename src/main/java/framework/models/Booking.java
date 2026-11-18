
package framework.models;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "roomid",
    "firstname",
    "lastname",
    "depositpaid",
    "bookingdates",
    "email",
    "phone"
})
@Generated("jsonschema2pojo")
public class Booking {

    @JsonProperty("roomid")
    private Integer roomid;
    @JsonProperty("firstname")
    private String firstname;
    @JsonProperty("lastname")
    private String lastname;
    @JsonProperty("depositpaid")
    private Boolean depositpaid;
    @JsonProperty("bookingdates")
    private Bookingdates bookingdates;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phone")
    private String phone;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("roomid")
    public Integer getRoomid() {
        return roomid;
    }

    @JsonProperty("roomid")
    public void setRoomid(Integer roomid) {
        this.roomid = roomid;
    }

    @JsonProperty("firstname")
    public String getFirstname() {
        return firstname;
    }

    @JsonProperty("firstname")
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @JsonProperty("lastname")
    public String getLastname() {
        return lastname;
    }

    @JsonProperty("lastname")
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @JsonProperty("depositpaid")
    public Boolean getDepositpaid() {
        return depositpaid;
    }

    @JsonProperty("depositpaid")
    public void setDepositpaid(Boolean depositpaid) {
        this.depositpaid = depositpaid;
    }

    @JsonProperty("bookingdates")
    public Bookingdates getBookingdates() {
        return bookingdates;
    }

    @JsonProperty("bookingdates")
    public void setBookingdates(Bookingdates bookingdates) {
        this.bookingdates = bookingdates;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Booking.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("roomid");
        sb.append('=');
        sb.append(((this.roomid == null)?"<null>":this.roomid));
        sb.append(',');
        sb.append("firstname");
        sb.append('=');
        sb.append(((this.firstname == null)?"<null>":this.firstname));
        sb.append(',');
        sb.append("lastname");
        sb.append('=');
        sb.append(((this.lastname == null)?"<null>":this.lastname));
        sb.append(',');
        sb.append("depositpaid");
        sb.append('=');
        sb.append(((this.depositpaid == null)?"<null>":this.depositpaid));
        sb.append(',');
        sb.append("bookingdates");
        sb.append('=');
        sb.append(((this.bookingdates == null)?"<null>":this.bookingdates));
        sb.append(',');
        sb.append("email");
        sb.append('=');
        sb.append(((this.email == null)?"<null>":this.email));
        sb.append(',');
        sb.append("phone");
        sb.append('=');
        sb.append(((this.phone == null)?"<null>":this.phone));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.firstname == null)? 0 :this.firstname.hashCode()));
        result = ((result* 31)+((this.bookingdates == null)? 0 :this.bookingdates.hashCode()));
        result = ((result* 31)+((this.phone == null)? 0 :this.phone.hashCode()));
        result = ((result* 31)+((this.depositpaid == null)? 0 :this.depositpaid.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.roomid == null)? 0 :this.roomid.hashCode()));
        result = ((result* 31)+((this.email == null)? 0 :this.email.hashCode()));
        result = ((result* 31)+((this.lastname == null)? 0 :this.lastname.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Booking) == false) {
            return false;
        }
        Booking rhs = ((Booking) other);
        return (((((((((this.firstname == rhs.firstname)||((this.firstname!= null)&&this.firstname.equals(rhs.firstname)))&&((this.bookingdates == rhs.bookingdates)||((this.bookingdates!= null)&&this.bookingdates.equals(rhs.bookingdates))))&&((this.phone == rhs.phone)||((this.phone!= null)&&this.phone.equals(rhs.phone))))&&((this.depositpaid == rhs.depositpaid)||((this.depositpaid!= null)&&this.depositpaid.equals(rhs.depositpaid))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.roomid == rhs.roomid)||((this.roomid!= null)&&this.roomid.equals(rhs.roomid))))&&((this.email == rhs.email)||((this.email!= null)&&this.email.equals(rhs.email))))&&((this.lastname == rhs.lastname)||((this.lastname!= null)&&this.lastname.equals(rhs.lastname))));
    }

}
