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

package io.questdb.cairo.vm;

import io.questdb.std.BinarySequence;
import io.questdb.std.FilesFacade;
import io.questdb.std.Long256;
import io.questdb.std.Long256Impl;

public abstract class AbstractContinuousMemory implements ContinuousReadOnlyMemory {
    private final ContinuousReadOnlyMemory.ByteSequenceView bsview = new ContinuousReadOnlyMemory.ByteSequenceView();
    private final ContinuousReadOnlyMemory.CharSequenceView csview = new ContinuousReadOnlyMemory.CharSequenceView();
    private final ContinuousReadOnlyMemory.CharSequenceView csview2 = new ContinuousReadOnlyMemory.CharSequenceView();
    private final Long256Impl long256 = new Long256Impl();
    private final Long256Impl long256B = new Long256Impl();
    protected long pageAddress = 0;
    protected FilesFacade ff;
    protected long fd = -1;
    protected long size = 0;
    protected long lim;
    protected long grownLength;

    public final BinarySequence getBin(long offset) {
        return getBin(offset, bsview);
    }

    @Override
    public long getPageAddress(int pageIndex) {
        return pageAddress;
    }

    @Override
    public int getPageCount() {
        return pageAddress == 0 ? 0 : 1;
    }

    public final CharSequence getStr(long offset) {
        return getStr(offset, csview);
    }

    public final CharSequence getStr2(long offset) {
        return getStr(offset, csview2);
    }

    public Long256 getLong256A(long offset) {
        getLong256(offset, long256);
        return long256;
    }

    public Long256 getLong256B(long offset) {
        getLong256(offset, long256B);
        return long256B;
    }

    public long size() {
        return size;
    }

    public long addressOf(long offset) {
        assert offset <= size : "offset=" + offset + ", size=" + size + ", fd=" + fd;
        return pageAddress + offset;
    }

    public long getFd() {
        return fd;
    }

    public FilesFacade getFilesFacade() {
        return ff;
    }

    public long getGrownLength() {
        return grownLength;
    }
}