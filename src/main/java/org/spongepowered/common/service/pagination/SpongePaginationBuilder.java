/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.common.service.pagination;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.spongepowered.api.command.CommandMessageFormatting.error;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.source.ProxySource;
import org.spongepowered.api.service.pagination.PaginationBuilder;
import org.spongepowered.api.service.pagination.PaginationCalculator;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.channel.MessageReceiver;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

class SpongePaginationBuilder implements PaginationBuilder {

    private final SpongePaginationService service;
    private Iterable<Text> contents;
    private Text title;
    private Text header;
    private Text footer;
    private String paginationSpacer = "=";

    private PaginationList paginationList;

    public SpongePaginationBuilder(SpongePaginationService service) {
        this.service = service;
    }

    @Override
    public PaginationBuilder contents(Iterable<Text> contents) {
        this.contents = contents;
        this.paginationList = null;
        return this;
    }

    @Override
    public PaginationBuilder contents(Text... contents) {
        this.contents = ImmutableList.copyOf(contents);
        this.paginationList = null;
        return this;
    }

    @Override
    public PaginationBuilder title(Text title) {
        this.title = title;
        this.paginationList = null;
        return this;
    }

    @Override
    public PaginationBuilder header(Text header) {
        this.header = header;
        this.paginationList = null;
        return this;
    }

    @Override
    public PaginationBuilder footer(Text footer) {
        this.footer = footer;
        this.paginationList = null;
        return this;
    }

    @Override
    public PaginationBuilder paddingString(String padding) {
        this.paginationSpacer = padding;
        this.paginationList = null;
        return this;
    }

    @Override
    public PaginationList build() {
        checkNotNull(this.contents, "contents");

        if (this.paginationList == null) {
            this.paginationList = new SpongePaginationList(this.service, this.contents, this.title, this.header, this.footer);
        }
        return this.paginationList;
    }

    @Override
    public void sendTo(MessageReceiver source) {
        this.build().sendTo(source);
    }

    @Override
    public void sendTo(MessageChannel channel) {
        this.build().sendTo(channel);
    }

    @Override
    public PaginationBuilder from(PaginationList list) {
        this.reset();
        this.contents = list.getContents();
        this.title = list.getTitle();
        this.header = list.getHeader();
        this.footer = list.getFooter();
        this.paginationSpacer = list.getPaddingString();

        this.paginationList = null;
        return this;
    }

    @Override
    public PaginationBuilder reset() {
        this.contents = null;
        this.title = null;
        this.header = null;
        this.footer = null;
        this.paginationSpacer = "=";

        this.paginationList = null;
        return this;
    }
}
