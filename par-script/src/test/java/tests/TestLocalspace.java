package tests;

import org.junit.Assert;
import org.junit.Test;
import org.ow2.parscript.PARScriptEngine;
import org.ow2.parscript.PARScriptFactory;
import org.ow2.proactive.scripting.ScriptResult;
import org.ow2.proactive.scripting.SimpleScript;
import org.ow2.proactive.scripting.TaskScript;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.assertNotNull;


/**
 * Basic PARScript tests.
 *
 * @author Activeeon Team
 */
public class TestLocalspace {

    @Test
    public void test() throws Exception {
        Path path = Paths.get(System.getProperty("java.io.tmpdir")).normalize();

        String rScript = "result=getwd();";

        Map<String, Object> aBindings =
                Collections.singletonMap(PARScriptEngine.DS_SCRATCH_BINDING_NAME, (Object)
                        path.toString());
        SimpleScript ss = new SimpleScript(rScript, PARScriptFactory.ENGINE_NAME);
        TaskScript taskScript = new TaskScript(ss);
        ScriptResult<Serializable> res = taskScript.execute(aBindings, System.out, System.err);

        String resPath = (String) res.getResult();
        assertNotNull("No result from R script", resPath);
        Assert.assertEquals("R script working directory is incorrect", path.toString(),
                resPath.replace("/", File.separator));
    }

}
