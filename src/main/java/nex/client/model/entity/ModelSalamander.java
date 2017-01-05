/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
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

//Fish2's code 
package nex.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelSalamander extends ModelBase
{
    private ModelRenderer frontRightLeg;
    private ModelRenderer frontLeftLeg;
    private ModelRenderer toothLeft;
    private ModelRenderer toothRight;
    private ModelRenderer jawLower;
    private ModelRenderer jawUpper;
    private ModelRenderer bodyFront;
    private ModelRenderer bodyBack;
    private ModelRenderer tail;
    private ModelRenderer hindRightLeg;
    private ModelRenderer hindLeftLeg;

    public ModelSalamander()
    {
        float scale = 0F;
        frontRightLeg = new ModelRenderer(this, 0, 18);
        frontRightLeg.addBox(-1.5F, -1.5F, -1.5F, 3, 6, 3, scale);
        frontRightLeg.setRotationPoint(-6F, 19.5F, -4.5F);

        frontLeftLeg = new ModelRenderer(this, 0, 18);
        frontLeftLeg.addBox(-1.5F, -1.5F, -1.5F, 3, 6, 3, scale);
        frontLeftLeg.setRotationPoint(6F, 19.5F, -4.5F);

        toothLeft = new ModelRenderer(this, 0, 27);
        toothLeft.addBox(1.5F, -6.5F, -1F, 1, 1, 1, scale);
        toothLeft.setRotationPoint(0F, 20.5F, -7.5F);
        toothLeft.rotateAngleX = 1.5708F;

        toothRight = new ModelRenderer(this, 0, 27);
        toothRight.addBox(-2.5F, -6.5F, -1F, 1, 1, 1, scale);
        toothRight.setRotationPoint(0F, 20.5F, -7.5F);
        toothRight.rotateAngleX = 1.5708F;

        jawLower = new ModelRenderer(this, 0, 9);
        jawLower.addBox(-3F, -7F, -2.5F, 6, 7, 2, scale);
        jawLower.setRotationPoint(0F, 20.5F, -7.5F);
        jawLower.rotateAngleX = 1.5708F;

        jawUpper = new ModelRenderer(this, 0, 0);
        jawUpper.addBox(-2.5F, -6.5F, 0F, 5, 6, 3, scale);
        jawUpper.setRotationPoint(0F, 20.5F, -7.5F);
        jawUpper.rotateAngleX = 1.5708F;

        bodyFront = new ModelRenderer(this, 16, 0);
        bodyFront.addBox(-4.5F, -5F, -3F, 9, 10, 6, scale);
        bodyFront.setRotationPoint(0F, 20F, -3F);
        bodyFront.rotateAngleX = 1.5708F;

        bodyBack = new ModelRenderer(this, 16, 16);
        bodyBack.addBox(-3.5F, -3.5F, -2.5F, 7, 7, 5, scale);
        bodyBack.setRotationPoint(0F, 20F, 5.5F);
        bodyBack.rotateAngleX = 1.5708F;

        tail = new ModelRenderer(this, 46, 0);
        tail.addBox(-2.5F, -3.5F, -1.5F, 5, 7, 3, scale);
        tail.setRotationPoint(0F, 20F, 12.5F);
        tail.rotateAngleX = 1.5708F;

        hindRightLeg = new ModelRenderer(this, 0, 18);
        hindRightLeg.addBox(-1.5F, -1.5F, -1.5F, 3, 6, 3, scale);
        hindRightLeg.setRotationPoint(-5F, 19.5F, 5.5F);

        hindLeftLeg = new ModelRenderer(this, 0, 18);
        hindLeftLeg.addBox(-1.5F, -1.5F, -1.5F, 3, 6, 3, scale);
        hindLeftLeg.setRotationPoint(5F, 19.5F, 5.5F);


    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {

        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        frontRightLeg.render(f5);
        frontLeftLeg.render(f5);
        toothLeft.renderWithRotation(f5);
        toothRight.renderWithRotation(f5);
        jawLower.renderWithRotation(f5);
        jawUpper.renderWithRotation(f5);
        bodyFront.renderWithRotation(f5);
        bodyBack.renderWithRotation(f5);
        tail.renderWithRotation(f5);
        hindRightLeg.render(f5);
        hindLeftLeg.render(f5);
    }

    private void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        frontRightLeg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        frontLeftLeg.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
        hindRightLeg.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
        hindLeftLeg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
    }
}