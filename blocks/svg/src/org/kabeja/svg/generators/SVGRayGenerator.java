/*
   Copyright 2008 Simon Mieth

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package org.kabeja.svg.generators;

import java.util.Map;

import org.kabeja.dxf.Bounds;
import org.kabeja.dxf.DXFEntity;
import org.kabeja.dxf.DXFRay;
import org.kabeja.dxf.helpers.DXFUtils;
import org.kabeja.dxf.helpers.Point;
import org.kabeja.math.TransformContext;
import org.kabeja.svg.SVGConstants;
import org.kabeja.svg.SVGContext;
import org.kabeja.svg.SVGUtils;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;


public class SVGRayGenerator extends AbstractSVGSAXGenerator {
    public void toSAX(ContentHandler handler, Map svgContext, DXFEntity entity,
        TransformContext transformContext) throws SAXException {
        DXFRay ray = (DXFRay) entity;
        Bounds b = (Bounds) svgContext.get(SVGContext.DRAFT_BOUNDS);

        // we will create a line, which goes over or to the end of the draft
        // bounds
        double t = Math.sqrt(Math.pow(b.getHeight(), 2) +
                Math.pow(b.getWidth(), 2));

        Point end = DXFUtils.getPointFromParameterizedLine(ray.getBasePoint(),
                ray.getDirection(), t);

        AttributesImpl atts = new AttributesImpl();
        SVGUtils.addAttribute(atts, "x1", "" + ray.getBasePoint().getX());
        SVGUtils.addAttribute(atts, "y1", "" + ray.getBasePoint().getY());
        SVGUtils.addAttribute(atts, "x2", "" + end.getX());
        SVGUtils.addAttribute(atts, "y2", "" + end.getY());
        super.setCommonAttributes(atts, svgContext, ray);

        SVGUtils.emptyElement(handler, SVGConstants.SVG_LINE, atts);
    }
}
