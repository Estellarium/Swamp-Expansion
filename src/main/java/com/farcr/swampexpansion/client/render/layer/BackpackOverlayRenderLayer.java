package com.farcr.swampexpansion.client.render.layer;

import com.farcr.swampexpansion.common.entity.SlabfishEntity;
import com.farcr.swampexpansion.common.entity.SlabfishOverlay;
import com.farcr.swampexpansion.core.SwampExpansion;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BackpackOverlayRenderLayer<E extends SlabfishEntity, M extends EntityModel<E>> extends LayerRenderer<E, M> {
	
	public BackpackOverlayRenderLayer(IEntityRenderer<E, M> entityRenderer) {
		super(entityRenderer);
	}
	
	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, E slabfish, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {		
		if(slabfish.getSlabfishOverlay() == SlabfishOverlay.NONE || slabfish.getSlabfishOverlay() == SlabfishOverlay.EGG || !slabfish.hasBackpack()) return;
		
		ResourceLocation texture = new ResourceLocation(SwampExpansion.MODID, "textures/entity/slabfish/overlays/" + slabfish.getSlabfishOverlay().getName() + "_overlay_backpack.png");
		Minecraft.getInstance().getTextureManager().bindTexture(texture);
		IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(texture));
		this.getEntityModel().setRotationAngles(slabfish, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		this.getEntityModel().render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
	}
	
}
