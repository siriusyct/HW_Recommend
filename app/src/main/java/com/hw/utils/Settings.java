/*******************************************************************************
 * Copyright 2013 momock.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.hw.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;

public class Settings{
	SharedPreferences settings;
	public Settings(Context context, String name){
		this(context, name, Context.MODE_PRIVATE);
	}
	public Settings(Context context, String name, int mode){
		settings = context.getSharedPreferences(name, mode);
	}

	public boolean hasProperty(String name) {
		return settings.contains(name);
	}

	public Object getProperty(String name) {
		if (hasProperty(name)){
			Map<String, ?> all = settings.getAll();
			return all.get(name);
		}
		return null;
	}
	public String getStringProperty(String name, String def){
		return settings.getString(name, def);
	}
	public int getIntProperty(String name, int def){
		return settings.getInt(name, def);
	}
	public long getLongProperty(String name, long def){
		return settings.getLong(name, def);
	}
	public float getFloatProperty(String name, float def){
		return settings.getFloat(name, def);
	}
	public boolean getBooleanProperty(String name, boolean def){
		return settings.getBoolean(name, def);
	}

	public List<String> getPropertyNames() {
		ArrayList<String> names = new ArrayList<String>();
		Map<String, ?> all = settings.getAll();
		names.addAll(all.keySet());
		return names;
	}

	public void setProperty(String name, Object val) {
		SharedPreferences.Editor prefEditor = settings.edit();  
		if (val == null)
			prefEditor.remove(name);
		else if (val instanceof CharSequence)
			prefEditor.putString(name, val.toString());
		else if (val instanceof Boolean)
			prefEditor.putBoolean(name, Convert.toBoolean(val));
		else if (val instanceof Double || val instanceof Float)
			prefEditor.putFloat(name, Convert.toDouble(val).floatValue());
		else if (val instanceof Integer)
			prefEditor.putInt(name, Convert.toInteger(val));
		else if (val instanceof Long)
			prefEditor.putLong(name, Convert.toLong(val));
		else
			prefEditor.putString(name, val.toString());
		prefEditor.commit();
	}
}
