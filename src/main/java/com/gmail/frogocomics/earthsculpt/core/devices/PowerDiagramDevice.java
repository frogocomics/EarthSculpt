package com.gmail.frogocomics.earthsculpt.core.devices;

import com.gmail.frogocomics.earthsculpt.core.ProjectSettings;
import com.gmail.frogocomics.earthsculpt.core.connection.HeightmapOutput;
import com.gmail.frogocomics.earthsculpt.core.connection.Input;
import com.gmail.frogocomics.earthsculpt.core.connection.Output;
import com.gmail.frogocomics.earthsculpt.core.data.HeightField;
import com.gmail.frogocomics.earthsculpt.core.parameters.DoubleParameter;
import com.gmail.frogocomics.earthsculpt.core.parameters.IntegerParameter;
import com.gmail.frogocomics.earthsculpt.core.parameters.Parameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kn.uni.voronoitreemap.datastructure.OpenList;
import kn.uni.voronoitreemap.diagram.PowerDiagram;
import kn.uni.voronoitreemap.j2d.PolygonSimple;
import kn.uni.voronoitreemap.j2d.Site;

//Todo: convert voronoi into various heights, incomplete!
public class PowerDiagramDevice extends DefaultDevice implements Device {

    private Input[] inputs = new Input[0];
    private Output[] outputs = new Output[1];
    private List<Parameter> parameters = new ArrayList<Parameter>();
    private boolean built = false;

    public PowerDiagramDevice() {
        parameters.add(new DoubleParameter("Scale", 0.25));
        parameters.add(new IntegerParameter(0, 65535, "Seed", 11540));
        outputs[0] = new HeightmapOutput(false, "Primary Output");
    }

    public String getName() {
        return "Power Diagram";
    }

    public Input[] getInputs() {
        return new Input[0];
    }

    public Output[] getOutputs() {
        return new Output[0];
    }

    public DeviceType getType() {
        return DeviceType.GENERATOR;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public boolean isBuilt() {
        return built;
    }

    public HeightField run() {
        /**
         * This might be hard to understand.
         *
         * For example, if I set the scale to 0.6
         * First, it inverts it, so 1 - 0.6 - 0.4
         * Multiply it by 100 to get 60
         * And lets say my project size is 1 km
         * So 60 x 1 = 60
         * Basically the maximum amount of points is 100 per square kilometer. (Minimum is 1 point)
         */

        int points = (int)((1 - (Double) parameters.get(0).getValue()) * 100) * (ProjectSettings.SIZE * ProjectSettings.SIZE);
        int seed = (Integer) parameters.get(1).getValue();
        PowerDiagram diagram = new PowerDiagram();
        OpenList sites = new OpenList();
        Random random = new Random(seed);
        PolygonSimple rootPolygon = new PolygonSimple(); //todo: perhaps are more natural shape?
        int width = ProjectSettings.WORLD_SIZE;
        int height = ProjectSettings.WORLD_SIZE;
        rootPolygon.add(0, 0);
        rootPolygon.add(width, 0);
        rootPolygon.add(width, height);
        rootPolygon.add(0, height);

        /**
         * Randomly generate a certain amount of points based on a seed.
         */

        for(int i = 0; i < points; i++) {
            Site site = new Site(random.nextInt(width), random.nextInt(width));
            sites.add(site);
        }
        diagram.setSites(sites);
        diagram.setClipPoly(rootPolygon);
        diagram.computeDiagram();
        for (int i=0;i<sites.size;i++){
            Site site=sites.array[i];
            PolygonSimple polygon = site.getPolygon();
        }

        return null;
    }

    public void setValue(String parameterName, Object value) {
        if(parameterName.equals("Scale")) {
            parameters.get(0).setValue(value);
        }
        if(parameterName.equals("Seed")) {
            parameters.get(1).setValue(value);
        }
    }

}

