package pl.hycom.jira.plugins.gitlab.integration.gitpanel.impl;

/*
 * <p>Copyright (c) 2016, Authors
 * Project:  gitlab-integration.</p>
 * Licence BSD 3-Clause:
 * <p>Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met:</p>
 * <p>1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following
 * disclaimer.</p>
 * <p>2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions
 * and the following disclaimer in the documentation and/or other materials provided with the distribution.</p>
 * <p>3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse
 * or promote products derived from this software without specific prior written permission.</p>
 * <p>THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.</p>
 */

import lombok.extern.log4j.Log4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.hycom.jira.plugins.gitlab.integration.gitpanel.api.ProcessorInterface;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
  * Tests of processor manager.
  * @author Augustyn Kończak <mailto:augustyn.konczak@hycom.pl> on 10.04.16.
  */
@Log4j
public class ProcessorManagerTest {

    @Autowired
    private ProcessorManager manager;

    private List<ProcessorInterface> processorsList;
    @Before
    public void setUp() {
        processorsList = new ArrayList<>();
        processorsList.add(new ProcessorInterface() {
            @Override
            public void execute(CommitData commitInfo) {
                log.info("inside processor execute, processing: " + commitInfo);
            }
        });
    }

    @Test
    public void referenceTest() {
        assertThat("Tested List is not empty", processorsList, is(notNullValue()));
        assertThat("Reference should be injected.", manager, is(notNullValue()));
    }
}
