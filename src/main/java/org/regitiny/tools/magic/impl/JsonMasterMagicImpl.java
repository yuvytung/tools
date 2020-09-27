package org.regitiny.tools.magic.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.JSONException;
import org.json.JSONObject;
import org.regitiny.tools.magic.JsonMasterMagic;
import org.regitiny.tools.magic.constant.StringPool;

@Log4j2
@NoArgsConstructor
@AllArgsConstructor
public class JsonMasterMagicImpl implements JsonMasterMagic
{

  private JSONObject jsonObject;

  private String defaultValue = null;

  public JsonMasterMagicImpl(JSONObject jsonObject)
  {
    this.jsonObject = jsonObject;
  }

  public JsonMasterMagicImpl(String json)
  {
    this.jsonObject = new JSONObject(json);
  }

  public JsonMasterMagicImpl(String json, String defaultValue)
  {
    this.jsonObject = new JSONObject(json);
    this.defaultValue = defaultValue;
  }

  public JsonMasterMagicImpl defaultValue(String defaultValue)
  {
    this.defaultValue = defaultValue;
    return this;
  }

  @Override
  public String getStringByKey(String keyInput)
  {

    if (keyInput == null)
      return defaultValue;
    String keys[] = keyInput.split(StringPool.DOT_IN_REGEX);



    try
    {
      JSONObject jsonObject = this.jsonObject;
      for (int i = 0; i < keys.length - 1; i++)
      {
        String key = keys[i];
        if (jsonObject.has(key) && !jsonObject.getJSONObject(key).isEmpty())
          jsonObject = jsonObject.getJSONObject(key);
        else
          return defaultValue;
      }
      String lastKey = keys[keys.length - 1];
      if (jsonObject.has(lastKey) && !jsonObject.getString(lastKey).isEmpty())
        return jsonObject.getString(lastKey);
    }
    catch (JSONException jsonException)
    {
      log.error("error JSONException", jsonException);
      return defaultValue;
    }
    return defaultValue;
  }

}
