/*
 * Copyright 2015-2102 RonCoo(http://www.roncoo.com) Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.roncoo.pay.permission.shiro.spring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.ehcache.Ehcache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.cache.support.SimpleValueWrapper;

/**
 * 包装Spring cache抽象.
 *
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
public class SpringCacheManagerWrapper implements CacheManager {

	private org.springframework.cache.CacheManager cacheManager;

	/**
	 * 设置spring cache manager
	 *
	 * @param cacheManager
	 */
	public void setCacheManager(org.springframework.cache.CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		org.springframework.cache.Cache springCache = cacheManager.getCache(name);
		return new SpringCacheWrapper(springCache);
	}

	@SuppressWarnings("rawtypes")
	static class SpringCacheWrapper implements Cache {
		private org.springframework.cache.Cache springCache;

		SpringCacheWrapper(org.springframework.cache.Cache springCache) {
			this.springCache = springCache;
		}

		@Override
		public Object get(Object key) throws CacheException {
			Object value = springCache.get(key);
			if (value instanceof SimpleValueWrapper) {
				return ((SimpleValueWrapper) value).get();
			}
			return value;
		}

		@Override
		public Object put(Object key, Object value) throws CacheException {
			springCache.put(key, value);
			return value;
		}

		@Override
		public Object remove(Object key) throws CacheException {
			springCache.evict(key);
			return null;
		}

		@Override
		public void clear() throws CacheException {
			springCache.clear();
		}

		@Override
		public int size() {
			if (springCache.getNativeCache() instanceof Ehcache) {
				Ehcache ehcache = (Ehcache) springCache.getNativeCache();
				return ehcache.getSize();
			}
			throw new UnsupportedOperationException("invoke spring cache abstract size method not supported");
		}

		@SuppressWarnings("unchecked")
		@Override
		public Set keys() {
			if (springCache.getNativeCache() instanceof Ehcache) {
				Ehcache ehcache = (Ehcache) springCache.getNativeCache();
				return new HashSet(ehcache.getKeys());
			}
			throw new UnsupportedOperationException("invoke spring cache abstract keys method not supported");
		}

		@SuppressWarnings("unchecked")
		@Override
		public Collection values() {
			if (springCache.getNativeCache() instanceof Ehcache) {
				Ehcache ehcache = (Ehcache) springCache.getNativeCache();
				List keys = ehcache.getKeys();
				if (!CollectionUtils.isEmpty(keys)) {
					List values = new ArrayList(keys.size());
					for (Object key : keys) {
						Object value = get(key);
						if (value != null) {
							values.add(value);
						}
					}
					return Collections.unmodifiableList(values);
				} else {
					return Collections.emptyList();
				}
			}
			throw new UnsupportedOperationException("invoke spring cache abstract values method not supported");
		}
	}
}
