package org.regitiny.tools.magic.quick;

import com.google.gson.Gson;
import org.json.JSONObject;

public class JsonQuickMagic
{
  public String toJsonString()
  {
    return new Gson().toJson(this);
  }

  public JSONObject toJsonObject()
  {
    return new JSONObject(toJsonString());
  }

  public <Clazz> Clazz fromJson(String json)
  {
    return (Clazz) new Gson().fromJson(json, this.getClass());
  }
}
