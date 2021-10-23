package cheaters.get.banned.utils;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.events.RenderEntityModelEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.EXTPackedDepthStencil;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

/**
 * Modified from LiquidBounce under GPL-3.0
 * https://github.com/CCBlueX/LiquidBounce/blob/legacy/LICENSE
 */
public class OutlineUtils {

    public static void outlineEntity(ModelBase model, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float scaleFactor, Color color) {
        boolean fancyGraphics = Shady.mc.gameSettings.fancyGraphics;
        float gamma = Shady.mc.gameSettings.gammaSetting;
        Shady.mc.gameSettings.fancyGraphics = false;
        Shady.mc.gameSettings.gammaSetting = Float.MAX_VALUE;

        GlStateManager.resetColor();
        setColor(color);

        renderOne(Config.espThickness);
        model.render(
                entity,
                limbSwing,
                limbSwingAmount,
                ageInTicks,
                headYaw,
                headPitch,
                scaleFactor
        );

        setColor(color);
        renderTwo();
        model.render(
                entity,
                limbSwing,
                limbSwingAmount,
                ageInTicks,
                headYaw,
                headPitch,
                scaleFactor
        );

        setColor(color);
        renderThree();
        model.render(
                entity,
                limbSwing,
                limbSwingAmount,
                ageInTicks,
                headYaw,
                headPitch,
                scaleFactor
        );

        setColor(color);
        renderFour(color);
        model.render(
                entity,
                limbSwing,
                limbSwingAmount,
                ageInTicks,
                headYaw,
                headPitch,
                scaleFactor
        );

        setColor(color);
        renderFive();

        setColor(Color.WHITE);
        Shady.mc.gameSettings.fancyGraphics = fancyGraphics;
        Shady.mc.gameSettings.gammaSetting = gamma;
    }

    public static void outlineEntity(RenderEntityModelEvent event, Color color) {
        outlineEntity(event.model, event.entity, event.limbSwing, event.limbSwingAmount, event.ageInTicks, event.headYaw, event.headPitch, event.scaleFactor, color);
    }

    private static void renderOne(final float lineWidth) {
        checkSetupFBO();
        glPushAttrib(GL_ALL_ATTRIB_BITS);
        glDisable(GL_ALPHA_TEST);
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_LIGHTING);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glLineWidth(lineWidth);
        glEnable(GL_LINE_SMOOTH);
        glEnable(GL_STENCIL_TEST);
        glClear(GL_STENCIL_BUFFER_BIT);
        glClearStencil(0xF);
        glStencilFunc(GL_NEVER, 1, 0xF);
        glStencilOp(GL_REPLACE, GL_REPLACE, GL_REPLACE);
        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    }

    private static void renderTwo() {
        glStencilFunc(GL_NEVER, 0, 0xF);
        glStencilOp(GL_REPLACE, GL_REPLACE, GL_REPLACE);
        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
    }

    private static void renderThree() {
        glStencilFunc(GL_EQUAL, 1, 0xF);
        glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    }

    private static void renderFour(final Color color) {
        setColor(color);
        glDepthMask(false);
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_POLYGON_OFFSET_LINE);
        glPolygonOffset(1.0F, -2000000F);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
    }

    private static void renderFive() {
        glPolygonOffset(1.0F, 2000000F);
        glDisable(GL_POLYGON_OFFSET_LINE);
        glEnable(GL_DEPTH_TEST);
        glDepthMask(true);
        glDisable(GL_STENCIL_TEST);
        glDisable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_DONT_CARE);
        glEnable(GL_BLEND);
        glEnable(GL_LIGHTING);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_ALPHA_TEST);
        glPopAttrib();
    }

    private static void setColor(final Color color) {
        GL11.glColor4d(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F);
    }

    private static void checkSetupFBO() {
        Framebuffer fbo = Minecraft.getMinecraft().getFramebuffer();
        if(fbo != null) {
            if(fbo.depthBuffer > -1) {
                setupFBO(fbo);
                fbo.depthBuffer = -1;
            }
        }
    }

    private static void setupFBO(Framebuffer fbo) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);
        int stencilDepthBufferId = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, stencilDepthBufferId);
        EXTFramebufferObject.glRenderbufferStorageEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, EXTPackedDepthStencil.GL_DEPTH_STENCIL_EXT, Shady.mc.displayWidth, Shady.mc.displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_STENCIL_ATTACHMENT_EXT, EXTFramebufferObject.GL_RENDERBUFFER_EXT, stencilDepthBufferId);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT, EXTFramebufferObject.GL_RENDERBUFFER_EXT, stencilDepthBufferId);
    }

}
