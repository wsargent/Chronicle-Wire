/*
 * Copyright 2016-2020 Chronicle Software
 *
 * https://chronicle.software
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.openhft.chronicle.wire;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SerializationStrategy<T> {
    @Deprecated(/*to be removed?*/)
    @Nullable
    default T read(ValueIn in, Class<T> type) {
        return readUsing(newInstanceOrNull(type), in);
    }

    @Nullable
    @Deprecated(/*to be removed?*/)
    default T readUsing(@Nullable T using, ValueIn in, @Nullable Class<T> type) {
        if (using == null && type != null)
            using = newInstanceOrNull(type);
        return readUsing(using, in);
    }

    @Nullable
    T readUsing(T using, ValueIn in);

    @Nullable
    T newInstanceOrNull(Class<T> type);

    Class<T> type();

    @NotNull
    BracketType bracketType();
}
