package russell.john.shared.action;

import com.gwtplatform.dispatch.shared.Action;

public class GetLogAction implements Action<GetLogResult> { 

  java.lang.String fbId;

  public GetLogAction(java.lang.String fbId) {
    this.fbId = fbId;
  }

  protected GetLogAction() {
    // Possibly for serialization.
  }

  public java.lang.String getFbId() {
    return fbId;
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
    GetLogAction other = (GetLogAction) obj;
    if (fbId == null) {
      if (other.fbId != null)
        return false;
    } else if (!fbId.equals(other.fbId))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (fbId == null ? 1 : fbId.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "GetLogAction["
                 + fbId
    + "]";
  }
}
