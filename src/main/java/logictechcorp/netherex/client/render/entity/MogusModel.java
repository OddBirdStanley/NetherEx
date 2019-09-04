/*
 * NetherEx
 * Copyright (c) 2016-2019 by LogicTechCorp
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package logictechcorp.netherex.client.render.entity;

import logictechcorp.netherex.entity.neutral.MogusEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MogusModel extends EntityModel<MogusEntity>
{
    private RendererModel cap;
    private RendererModel topCap;
    private RendererModel body;
    private RendererModel rightLeg;
    private RendererModel leftLeg;
    private RendererModel rightArm;
    private RendererModel leftArm;

    public MogusModel()
    {
        this.cap = new RendererModel(this, 0, 0);
        this.cap.addBox(-2F, 0F, -2F, 5, 1, 5);
        this.cap.setRotationPoint(-1F, 18F, 0F);
        this.cap.setTextureSize(64, 32);
        this.cap.mirror = true;
        this.topCap = new RendererModel(this, 20, 0);
        this.topCap.addBox(-1F, -1F, -1F, 3, 1, 3);
        this.topCap.setRotationPoint(-1F, 18F, 0F);
        this.topCap.setTextureSize(64, 32);
        this.topCap.mirror = true;
        this.body = new RendererModel(this, 0, 6);
        this.body.addBox(-2F, 1F, -1F, 3, 4, 3);
        this.body.setRotationPoint(0F, 18F, 0F);
        this.body.setTextureSize(64, 32);
        this.body.mirror = true;
        this.rightLeg = new RendererModel(this, 12, 11);
        this.rightLeg.addBox(-1F, 0F, 0F, 1, 1, 1);
        this.rightLeg.setRotationPoint(-1F, 23F, 0F);
        this.rightLeg.setTextureSize(64, 32);
        this.rightLeg.mirror = true;
        this.leftLeg = new RendererModel(this, 12, 11);
        this.leftLeg.addBox(0F, 0F, 0F, 1, 1, 1);
        this.leftLeg.setRotationPoint(0F, 23F, 0F);
        this.leftLeg.setTextureSize(64, 32);
        this.leftLeg.mirror = true;
        this.rightArm = new RendererModel(this, 16, 10);
        this.rightArm.addBox(-1F, 0F, 0F, 1, 2, 1);
        this.rightArm.setRotationPoint(-2F, 20F, 0F);
        this.rightArm.setTextureSize(64, 32);
        this.rightArm.mirror = true;
        this.leftArm = new RendererModel(this, 16, 10);
        this.leftArm.addBox(0F, 0F, 0F, 1, 2, 1);
        this.leftArm.setRotationPoint(1F, 20F, 0F);
        this.leftArm.setTextureSize(64, 32);
        this.leftArm.mirror = true;
    }

    @Override
    public void render(MogusEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scaleFactor)
    {
        this.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scaleFactor);
        this.cap.render(scaleFactor);
        this.topCap.render(scaleFactor);
        this.body.render(scaleFactor);
        this.leftLeg.render(scaleFactor);
        this.rightLeg.render(scaleFactor);
        this.rightArm.render(scaleFactor);
        this.leftArm.render(scaleFactor);
    }

    @Override
    public void setRotationAngles(MogusEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scaleFactor)
    {
        this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
        this.rightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
        this.leftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }
}
