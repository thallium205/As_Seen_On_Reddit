package russell.john.shared.action;

import com.gwtplatform.dispatch.shared.Result;

public class FacebookResult implements Result { 

  java.lang.String authToken;
  java.lang.String fbId;
  java.lang.String fbName;
  java.util.ArrayList<russell.john.shared.action.FacebookFriendType> fbFriends;

  public FacebookResult(java.lang.String authToken, java.lang.String fbId, java.lang.String fbName, java.util.ArrayList<russell.john.shared.action.FacebookFriendType> fbFriends) {
    this.authToken = authToken;
    this.fbId = fbId;
    this.fbName = fbName;
    this.fbFriends = fbFriends;
  }

  protected FacebookResult() {
    // Possibly for serialization.
  }

  public java.lang.String getAuthToken() {
    return authToken;
  }

  public java.lang.String getFbId() {
    return fbId;
  }

  public java.lang.String getFbName() {
    return fbName;
  }

  public java.util.ArrayList<russell.john.shared.action.FacebookFriendType> getFbFriends() {
    return fbFriends;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    FacebookResult other = (FacebookResult) obj;
    if (authToken == null) {
      if (other.authToken != null)
        return false;
    } else if (!authToken.equals(other.authToken))
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
    if (fbFriends == null) {
      if (other.fbFriends != null)
        return false;
    } else if (!fbFriends.equals(other.fbFriends))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (authToken == null ? 1 : authToken.hashCode());
    hashCode = (hashCode * 37) + (fbId == null ? 1 : fbId.hashCode());
    hashCode = (hashCode * 37) + (fbName == null ? 1 : fbName.hashCode());
    hashCode = (hashCode * 37) + (fbFriends == null ? 1 : fbFriends.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "FacebookResult["
                 + authToken
                 + ","
                 + fbId
                 + ","
                 + fbName
                 + ","
                 + fbFriends
    + "]";
  }
}
