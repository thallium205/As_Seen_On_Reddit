package russell.john.shared.action;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GetLogDto implements IsSerializable { 

  java.lang.String fbId;
  java.util.ArrayList<russell.john.shared.action.GetLogType> results;

  public GetLogDto(java.lang.String fbId, java.util.ArrayList<russell.john.shared.action.GetLogType> results) {
    this.fbId = fbId;
    this.results = results;
  }

  protected GetLogDto() {
    // Possibly for serialization.
  }

  public java.lang.String getFbId() {
    return fbId;
  }

  public java.util.ArrayList<russell.john.shared.action.GetLogType> getResults() {
    return results;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    GetLogDto other = (GetLogDto) obj;
    if (fbId == null) {
      if (other.fbId != null)
        return false;
    } else if (!fbId.equals(other.fbId))
      return false;
    if (results == null) {
      if (other.results != null)
        return false;
    } else if (!results.equals(other.results))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (fbId == null ? 1 : fbId.hashCode());
    hashCode = (hashCode * 37) + (results == null ? 1 : results.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "GetLogDto["
                 + fbId
                 + ","
                 + results
    + "]";
  }
}
