/*
 *  Copyright 2007-2016 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package biz.deinum.multitenant.aop.target.registry;

import org.springframework.core.Ordered;

/**
 * Generic implementation and functionality for a {@link TargetRegistry}. 
 * This class provides optional chaining of {@code TargetRegistry} instances.
 * 
 * @author Marten Deinum
 * @since 1.1
 */
public abstract class AbstractTargetRegistry<T> implements TargetRegistry<T>, Ordered {

    private TargetRegistry<T> parent = null;

    private int order;

    public final void setParentRegistry(final TargetRegistry<T> registry) {
        this.parent = registry;
    }

    /**
     * {@inheritDoc}
     */
    public final T getTarget(final String context) {
        T target = this.getTargetInternal(context);
        if (target == null && this.parent != null) {
            target = this.parent.getTarget(context);
        }
        return target;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(final int order) {
        this.order = order;
    }

    /**
     * Retrieve the target. Subclasses must implement this method.
     * 
     * @param context the context to use for the lookup
     * @return the found target object
     *
     * @see #getTarget(String)
     */
    protected abstract T getTargetInternal(final String context);

}
