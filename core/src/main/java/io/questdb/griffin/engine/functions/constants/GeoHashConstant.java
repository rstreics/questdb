/*******************************************************************************
 *     ___                  _   ____  ____
 *    / _ \ _   _  ___  ___| |_|  _ \| __ )
 *   | | | | | | |/ _ \/ __| __| | | |  _ \
 *   | |_| | |_| |  __/\__ \ |_| |_| | |_) |
 *    \__\_\\__,_|\___||___/\__|____/|____/
 *
 *  Copyright (c) 2014-2019 Appsicle
 *  Copyright (c) 2019-2020 QuestDB
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 ******************************************************************************/

package io.questdb.griffin.engine.functions.constants;

import io.questdb.cairo.ColumnType;
import io.questdb.cairo.GeoHashExtra;
import io.questdb.cairo.sql.Record;
import io.questdb.griffin.engine.functions.GeoHashFunction;
import io.questdb.griffin.engine.functions.geohash.GeoHashNative;
import io.questdb.std.Chars;
import io.questdb.std.Numbers;

public class GeoHashConstant extends GeoHashFunction implements ConstantFunction {
    public static final GeoHashConstant NULL = new GeoHashConstant(Numbers.LONG_NaN, ColumnType.GEOHASH);

    private final long hashz;

    public GeoHashConstant(long hashz, int typep) {
        assert ColumnType.GEOHASH == ColumnType.tagOf(typep);
        this.hashz = hashz;
        this.typep = typep;
    }

    public GeoHashConstant(CharSequence value) {
        if (value == null || value.length() == 0) {
            hashz = Numbers.LONG_NaN;
        } else {
            CharSequence str = value;
            if (Chars.startsWith(value, '\'') && Chars.endsWith(value, '\'')) {
                str = Chars.toString(value, 1, value.length() - 1);
            }
            hashz = GeoHashNative.fromString(str);
            typep = GeoHashExtra.setBitsPrecision(ColumnType.GEOHASH, str.length() * 5);
        }
    }

    public static GeoHashConstant newInstance(long hashz, int type) {
        return hashz != Numbers.LONG_NaN ? new GeoHashConstant(hashz, type) : NULL;
    }

    public static GeoHashConstant newInstance(CharSequence value) {
        return value != null && value.length() > 0 ? new GeoHashConstant(value) : NULL;
    }

    @Override
    public final long getLong(Record rec) {
        return hashz;
    }

    @Override
    public final long getGeoHash(Record rec) {
        return hashz;
    }
}
