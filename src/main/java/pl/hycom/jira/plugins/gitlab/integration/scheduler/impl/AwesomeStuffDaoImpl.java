package pl.hycom.jira.plugins.gitlab.integration.scheduler.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomeException;
import pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomeStuff;
import pl.hycom.jira.plugins.gitlab.integration.scheduler.AwesomeStuffDao;

/**
 * Simple memory-based DAO for awesome stuff.
 * <p>Copyright (c) 2016, Authors</p>
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at</p>
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0</p>
 *
 * <p>Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.</p>
 * @since v1.0
 */
@Component
public class AwesomeStuffDaoImpl implements AwesomeStuffDao
{
    @Autowired
    private ActiveObjects ao;

    @Override
    public AwesomeStuff findById(int id) throws AwesomeException
    {
        return ao.get(AwesomeStuff.class, id);
    }

    @Override
    public AwesomeStuff[] findByAll() throws AwesomeException
    {
        return ao.find(AwesomeStuff.class);
    }

    @Override
    public AwesomeStuff create(String name) throws AwesomeException
    {
        if (name == null || name.isEmpty())
        {
            throw new AwesomeException("Please specify a name for your awesome stuff");
        }
        final AwesomeStuff stuff = ao.create(AwesomeStuff.class);
        stuff.setName(name);
        stuff.save();
        return stuff;
    }

    @Override
    public void remove(AwesomeStuff stuff) throws AwesomeException
    {
        ao.delete(stuff);
    }
}
