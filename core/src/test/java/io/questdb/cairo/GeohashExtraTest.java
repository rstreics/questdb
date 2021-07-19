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

package io.questdb.cairo;
import org.junit.Assert;
import org.junit.Test;

public class GeohashExtraTest {
    @Test
    public void testBitsPrecision() {
        Assert.assertEquals(ColumnType.GEOHASH, ColumnType.tagOf(ColumnType.GEOHASH));
        Assert.assertEquals(0, GeohashExtra.getBitsPrecision(ColumnType.GEOHASH));

        int geohashCol = GeohashExtra.setBitsPrecision(ColumnType.GEOHASH, 42);
        Assert.assertEquals(ColumnType.GEOHASH, ColumnType.tagOf(geohashCol));
        Assert.assertEquals(42, GeohashExtra.getBitsPrecision(geohashCol));
        geohashCol = GeohashExtra.setBitsPrecision(geohashCol, 24);
        Assert.assertEquals(24, GeohashExtra.getBitsPrecision(geohashCol));
    }
}