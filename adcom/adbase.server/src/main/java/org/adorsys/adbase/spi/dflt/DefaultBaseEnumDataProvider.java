package org.adorsys.adbase.spi.dflt;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ejb.Stateless;

import org.adorsys.adbase.spi.BaseEnumDataProvider;
import org.adorsys.adcore.enums.CoreDocTypeEnum;
import org.adorsys.adcore.enums.CoreHistoryTypeEnum;
import org.adorsys.adcore.enums.CoreProcStepEnum;
import org.adorsys.adcore.enums.CoreProcessStatusEnum;
import org.adorsys.adcore.enums.CoreRoleInProcessEnum;
import org.adorsys.adcore.enums.CoreSttlmtOpEnum;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class DefaultBaseEnumDataProvider implements BaseEnumDataProvider {
	
	@Override
	public Map<String, Map<String, List<String>>> getDocTypeData() {
		CoreDocTypeEnum[] values = CoreDocTypeEnum.values();
		List<String> keys = new ArrayList<String>(values.length);
		for (CoreDocTypeEnum e : values) {
			keys.add(e.name());
		}
		return load(CoreDocTypeEnum.class, keys);
	}

	@Override
	public Map<String, Map<String, List<String>>> getHistoryTypeData() {
		CoreHistoryTypeEnum[] values = CoreHistoryTypeEnum.values();
		List<String> keys = new ArrayList<String>(values.length);
		for (CoreHistoryTypeEnum e : values) {
			keys.add(e.name());
		}
		return load(CoreHistoryTypeEnum.class, keys);
	}

	@Override
	public Map<String, Map<String, List<String>>> getProcessStatusData() {
		CoreProcessStatusEnum[] values = CoreProcessStatusEnum.values();
		List<String> keys = new ArrayList<String>(values.length);
		for (CoreProcessStatusEnum e : values) {
			keys.add(e.name());
		}
		return load(CoreProcessStatusEnum.class, keys);
	}

	@Override
	public Map<String, Map<String, List<String>>> getProcStepData() {
		CoreProcStepEnum[] values = CoreProcStepEnum.values();
		List<String> keys = new ArrayList<String>(values.length);
		for (CoreProcStepEnum e : values) {
			keys.add(e.name());
		}
		return load(CoreProcStepEnum.class, keys);
	}

	@Override
	public Map<String, Map<String, List<String>>> getRoleInProcessData() {
		CoreRoleInProcessEnum[] values = CoreRoleInProcessEnum.values();
		List<String> keys = new ArrayList<String>(values.length);
		for (CoreRoleInProcessEnum e : values) {
			keys.add(e.name());
		}
		return load(CoreRoleInProcessEnum.class, keys);
	}

	@Override
	public Map<String, Map<String, List<String>>> getSttlmtOpData() {
		CoreSttlmtOpEnum[] values = CoreSttlmtOpEnum.values();
		List<String> keys = new ArrayList<String>(values.length);
		for (CoreSttlmtOpEnum e : values) {
			keys.add(e.name());
		}
		return load(CoreSttlmtOpEnum.class, keys);
	}

	private Map<String, Map<String, List<String>>> load(Class<?> enumKlass, List<String> keys) {
		Map<String, Map<String, List<String>>> result = new HashMap<String, Map<String, List<String>>>();
		try {
			InputStream inputStream = enumKlass.getResourceAsStream(enumKlass.getSimpleName()+".properties");
			Properties enProps = new Properties();
			enProps.load(inputStream);
			inputStream = enumKlass.getResourceAsStream(enumKlass.getSimpleName()+"_fr.properties");
			Properties frProps = new Properties();
			frProps.load(inputStream);
			String simpleName = enumKlass.getSimpleName();
			for (String enumKey : keys) {
				String titleStr = simpleName+"_"+enumKey+"_description.title";
				String textStr = simpleName+"_"+enumKey+"_description.text";
				String enTitle = enProps.getProperty(titleStr);
				String enText = enProps.getProperty(textStr);
				String frTitle = frProps.getProperty(titleStr);
				String frText = frProps.getProperty(textStr);
				if(StringUtils.isBlank(enTitle) && StringUtils.isBlank(enText) && StringUtils.isBlank(frTitle) && StringUtils.isBlank(frText)){
					continue;
				}
				Map<String, List<String>> keyMap = new HashMap<String, List<String>>();
				result.put(enumKey, keyMap);
				if(StringUtils.isNotBlank(enTitle) || StringUtils.isNotBlank(enText)){
					List<String> enList = new ArrayList<String>();
					keyMap.put("en", enList);
					if(StringUtils.isNotBlank(enTitle)){
						enList.add(enTitle);
					}
					if(StringUtils.isNotBlank(enText)){
						enList.add(enText);
					}
				}
				if(StringUtils.isNotBlank(frTitle) || StringUtils.isNotBlank(frText)){
					List<String> frList = new ArrayList<String>();
					keyMap.put("fr", frList);
					if(StringUtils.isNotBlank(frTitle)){
						frList.add(frTitle);
					}
					if(StringUtils.isNotBlank(frText)){
						frList.add(frText);
					}
				}
			}
		} catch (IOException e) {
			// noop
		}
		return result;
	}
	
}
