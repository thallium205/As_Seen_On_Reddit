package russell.john.shared.action;

import com.gwtplatform.dispatch.shared.Action;

public class FacebookAction implements Action<FacebookResult> { 

  java.lang.String authCode;

  public FacebookAction(java.lang.String authCode) {
    this.authCode = authCode;
  }

  protected FacebookAction() {
    // Possibly for serialization.
  }

  public java.lang.String getAuthCode() {
    return authCode;
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
    FacebookAction other = (FacebookAction) obj;
    if (authCode == null) {
      if (other.authCode != null)
        return false;
    } else if (!authCode.equals(other.authCode))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (authCode == null ? 1 : authCode.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "FacebookAction["
                 + authCode
    + "]";
  }
}
