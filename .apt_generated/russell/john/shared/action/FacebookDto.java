package russell.john.shared.action;

import com.google.gwt.user.client.rpc.IsSerializable;

public class FacebookDto implements IsSerializable { 

  java.lang.String authCode;
  java.lang.String authToken;
  java.util.ArrayList<russell.john.shared.action.FacebookFriendType> fbFriends;
  java.lang.String fbId;
  java.lang.String fbName;

  public FacebookDto(java.lang.String authCode, java.lang.String authToken, java.util.ArrayList<russell.john.shared.action.FacebookFriendType> fbFriends, java.lang.String fbId, java.lang.String fbName) {
    this.authCode = authCode;
    this.authToken = authToken;
    this.fbFriends = fbFriends;
    this.fbId = fbId;
    this.fbName = fbName;
  }

  protected FacebookDto() {
    // Possibly for serialization.
  }

  public java.lang.String getAuthCode() {
    return authCode;
  }

  public java.lang.String getAuthToken() {
    return authToken;
  }

  public java.util.ArrayList<russell.john.shared.action.FacebookFriendType> getFbFriends() {
    return fbFriends;
  }

  public java.lang.String getFbId() {
    return fbId;
  }

  public java.lang.String getFbName() {
    return fbName;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    FacebookDto other = (FacebookDto) obj;
    if (authCode == null) {
      if (other.authCode != null)
        return false;
    } else if (!authCode.equals(other.authCode))
      return false;
    if (authToken == null) {
      if (other.authToken != null)
        return false;
    } else if (!authToken.equals(other.authToken))
      return false;
    if (fbFriends == null) {
      if (other.fbFriends != null)
        return false;
    } else if (!fbFriends.equals(other.fbFriends))
      return false;
    if (fbId == null) {
      if (other.fbId != null)
        return false;
    } else if (!fbId.equals(other.fbId))
      return false;
    if (fbName == null) {
      if (other.fbName != null)
        return false;
    } else if (!fbName.equals(other.fbName))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (authCode == null ? 1 : authCode.hashCode());
    hashCode = (hashCode * 37) + (authToken == null ? 1 : authToken.hashCode());
    hashCode = (hashCode * 37) + (fbFriends == null ? 1 : fbFriends.hashCode());
    hashCode = (hashCode * 37) + (fbId == null ? 1 : fbId.hashCode());
    hashCode = (hashCode * 37) + (fbName == null ? 1 : fbName.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "FacebookDto["
                 + authCode
                 + ","
                 + authToken
                 + ","
                 + fbFriends
                 + ","
                 + fbId
                 + ","
                 + fbName
    + "]";
  }
}
