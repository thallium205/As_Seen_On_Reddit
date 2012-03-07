package russell.john.shared.action;

import com.gwtplatform.dispatch.shared.Action;

public class GetSettingsAction implements Action<GetSettingsResult> { 

  java.lang.String fbId;
  java.lang.String authToken;

  public GetSettingsAction(java.lang.String fbId, java.lang.String authToken) {
    this.fbId = fbId;
    this.authToken = authToken;
  }

  protected GetSettingsAction() {
    // Possibly for serialization.
  }

  public java.lang.String getFbId() {
    return fbId;
  }

  public java.lang.String getAuthToken() {
    return authToken;
  }

  @Override
  public String getServiceName() {
    return "dispatch/";
  }

  @Override
  public boolean isSecured() {
    return false;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    GetSettingsAction other = (GetSettingsAction) obj;
    if (fbId == null) {
      if (other.fbId != null)
        return false;
    } else if (!fbId.equals(other.fbId))
      return false;
    if (authToken == null) {
      if (other.authToken != null)
        return false;
    } else if (!authToken.equals(other.authToken))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (fbId == null ? 1 : fbId.hashCode());
    hashCode = (hashCode * 37) + (authToken == null ? 1 : authToken.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "GetSettingsAction["
                 + fbId
                 + ","
                 + authToken
    + "]";
  }
}
