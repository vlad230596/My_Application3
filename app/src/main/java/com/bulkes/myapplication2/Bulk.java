package com.bulkes.myapplication2;

import android.graphics.Path;
import android.util.Log;

/**
 * Created by progr on 10.03.2016.
 */
public class Bulk extends Unit
{
    protected  boolean      isMoved;
    protected  float        mass;
    protected Indicator indicator;
    public Bulk(float _x, float _y, float _radius, int _color)
    {
        super(_x,_y, _radius, _color);
        mass = (float)Math.PI * _radius * _radius;
        isMoved = false;
        indicator = new Indicator();
        animationRadius = radius;
    }
    public Bulk( float _x, float _y, float _radius)
    {
        this(_x, _y, _radius, Settings.BulkDefaultColor);
    }

    public float getSpeedCoefficient()
    {
        return Math.min(Settings.BulkBaseSize / radius, 2f);
    }

    @Override
    public float getSpeedX()
    {
        return speedX * getSpeedCoefficient();
    }

    @Override
    public float getSpeedY()
    {
        return speedY * getSpeedCoefficient();
    }

    public boolean getIsMoved()
    {
        return isMoved;
    }

    public void setIsMoved(boolean flag)
    {
        isMoved = flag;
    }

    public void addMass(float feed)
    {
        setMass(feed + mass);
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass)
    {
        this.mass = mass;
        radius = (float) Math.sqrt((double) mass / Math.PI) * Settings.UserScale;
        baseRadius = radius;
        if( this instanceof User) {
            Log.v("Mass: ", String.valueOf(mass));
            Log.v("Radius: ", String.valueOf(radius));
        }
    }

    @Override
    public void updatePosition(Unit unit)//update location + radius
    {
        radius = (float) Math.sqrt((double) mass / Math.PI) * Settings.UserScale;
        //baseRadius = radius;
        x = unit.x + ((baseX - unit.x) * Settings.UserScale);
        y = unit.y + ((baseY - unit.y) * Settings.UserScale);
        if (this instanceof User) {
            Log.v("User UPD", String.valueOf(radius));
        }
        if(!isOnMainScreen())
            animationRadius = radius;
    }

    @Override
    public float getFeed()
    {
        return mass;
    }

    public Path getIndicator(float x_end, float y_end)
    {
        float coefficient = Math.max(radius / Settings.BulkBaseSize, 2f);
        indicator.getParameters(x,y,getAnimationRadius() + Settings.IndicatorTopOffset * coefficient, x_end, y_end);
        return indicator.getTriangle(x, y, getAnimationRadius() + (Settings.IndicatorBaseOffset * coefficient), coefficient);
    }
}