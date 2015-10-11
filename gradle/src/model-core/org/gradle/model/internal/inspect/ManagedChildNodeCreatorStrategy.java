/*
 * Copyright 2015 the original author or authors.
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

package org.gradle.model.internal.inspect;

import org.gradle.model.internal.core.ChildNodeInitializerStrategy;
import org.gradle.model.internal.core.NodeInitializer;
import org.gradle.model.internal.manage.schema.ManagedImplModelSchema;
import org.gradle.model.internal.manage.schema.ModelSchema;
import org.gradle.model.internal.manage.schema.ModelSchemaStore;
import org.gradle.model.internal.type.ModelType;

public class ManagedChildNodeCreatorStrategy<T> implements ChildNodeInitializerStrategy<T> {

    private final ModelSchemaStore modelSchemaStore;

    public ManagedChildNodeCreatorStrategy(ModelSchemaStore modelSchemaStore) {
        this.modelSchemaStore = modelSchemaStore;
    }

    @Override
    public <S extends T> NodeInitializer initializer(ModelType<S> type) {
        ModelSchema<S> schema = modelSchemaStore.getSchema(type);
        if (!(schema instanceof ManagedImplModelSchema)) {
            throw new IllegalArgumentException("Type is not managed: " + type);
        }
        return ((ManagedImplModelSchema<S>) schema).getNodeInitializer();
    }

}