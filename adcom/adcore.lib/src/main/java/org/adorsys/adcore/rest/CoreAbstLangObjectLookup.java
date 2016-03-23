package org.adorsys.adcore.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adorsys.adcore.jpa.CoreAbstLangObject;
import org.adorsys.adcore.vo.StringListHolder;
import org.apache.commons.lang3.StringUtils;

public abstract class CoreAbstLangObjectLookup<E extends CoreAbstLangObject>
		extends CoreAbstIdentifLookup<E> {

	/**
	 * Always try to find the matching of the given langIso2, if not select. 
	 */
	@Override
	public List<E> findByCntnrIdentifIn(StringListHolder holder) {
		String langIso2 = holder.getLangIso2();
		Set<E> result = new HashSet<>();
		for (String cntnrIdentif : holder.getList()) {
			E uniqueFound = null;
			if(StringUtils.isNotBlank(langIso2)) {
				String identif = CoreAbstLangObject.toIdentif(cntnrIdentif, langIso2);
				uniqueFound = findByIdentif(identif);
			}
			List<E> found = null;
			if(uniqueFound==null){
				found = findByCntnrIdentif(cntnrIdentif, holder.getStart(), holder.getMax());
			} else {
				found = Arrays.asList(uniqueFound);
			}
			result.addAll(found);
		}
		return new ArrayList<>(result);
	}
}
