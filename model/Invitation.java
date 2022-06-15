package model;

import java.io.Serializable;

public class Invitation implements Serializable {
    private int inviterID;
    private String inviterEmail;


    private String invitedEmail;
    private String invitationCode;

    public Invitation(int inviterID, String inviterEmail, String invitedEmail, String invitationCode) {
        this.inviterID = inviterID;
        this.inviterEmail = inviterEmail;
        this.invitedEmail = invitedEmail;
        this.invitationCode = invitationCode;
    }

    public void setInviterID(int inviterID) {
        this.inviterID = inviterID;
    }

    public void setInviterEmail(String inviterEmail) {
        this.inviterEmail = inviterEmail;
    }

    public void setInvitedEmail(String invitedEmail) {
        this.invitedEmail = invitedEmail;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public int getInviterID() {
        return inviterID;
    }

    public String getInviterEmail() {
        return inviterEmail;
    }



    public String getInvitedEmail() {
        return invitedEmail;
    }


    public String getInvitationCode() {
        return invitationCode;
    }




}
