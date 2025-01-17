/**************************************************************
Class: Point, Line, and Circle
Author: Alex Henniges, Tom Williams, Mitch Wilson
Version: July 28, 2008
***************************************************************
This file holds the information for the three classes Point,
Line, and Circle. These classes can be used independently of a
triangulation, but are useful for providing coordinates to such
a system. Currently implemented as a two-dimensional model.
**************************************************************/

#include <cmath>
#include "pointlinecircle.h"

using namespace std;


Point::Point(double xi, double yi)
{
   x = xi;
   y = yi;
}
Point::Point()
{
   x = 0;
   y = 0;
}
Point::~Point()
{
}

Line::Line(double xi1, double yi1, double xi2, double yi2)
{
    x1 = xi1;
    y1 = yi1;
    x2 = xi2;
    y2 = yi2;
    slope = 0;
    intercept = 0;
    vertical = x1 == x2;
    if(!vertical)
    {
      slope = (y2 - y1)/(x2 - x1);
      intercept = y1 - slope * x1;
    }
}
Line::Line(Point init, Point end)
{
    x1 = init.x;
    y1 = init.y;
    x2 = end.x;
    y2 = end.y;
    slope = 0;
    intercept = 0;
    vertical = x1 == x2;
    if(!vertical)
    {
      slope = (y2 - y1)/(x2 - x1);
      intercept = y1 - slope * x1;
    }            
}
Line::Line()
{
    x1 = 0;
    y1 = 0;
    x2 = 0;
    y2 = 0;
    slope = 0;
    intercept = 0;
    vertical = 0;
}
Line::~Line()
{
}

double Line::getInitialX()
{
    return x1;
}
double Line::getInitialY()
{
    return y1;
}
double Line::getEndingX()
{
    return x2;
}
double Line::getEndingY()
{
    return y2;
}
Point Line::getInitial()
{
      Point p(x1, y1);
      return p;
}
Point Line::getEnding()
{
      Point p(x2, y2);
      return p;
}
double Line::getSlope()
{
    return slope;
}

double Line::getIntercept()
{
    return intercept;
}

bool Line::isVertical()
{
     return vertical;
}

bool Line::isAbove(double x, double y)
{
     if(vertical)
     {
        return x < x1;
     }
     double yVal = slope * x + intercept;
     return y < yVal;
}

bool Line::isAbove(Point p)
{
     return isAbove(p.x, p.y);
}

bool Line::hasPoint(double x, double y)
{
      if(vertical)
     {
        return x == x1;
     }
     double yVal = slope * x + intercept;
     return y == yVal;
}

bool Line::hasPoint(Point p)
{
     return hasPoint(p.x, p.y);
}

double Line::getLength()
{
     return sqrt(pow((x1-x2), 2) + pow((y1-y2), 2));
}

Point Line::intersection(Line l)
{    
     double xp, yp;
     if(isVertical())
     {
          if(l.isVertical())
              throw 0;
          else
          {
               yp = l.getSlope() * x1 + l.getIntercept();
               xp = x1;
               Point p(xp, yp);
               return p;
          }
     }
     
     if(l.isVertical())
     {
          yp = getSlope() * l.getInitialX() + getIntercept();
          xp = l.getInitialX();
          Point p(xp, yp);
          return p;
     }
     
     if(l.getSlope() == slope)
         throw 0;
     
     xp = (getIntercept() - l.getIntercept()) / (l.getSlope() - getSlope());
     yp = xp * slope + intercept;
     
     Point p(xp, yp);
     return p;
}

Circle::Circle(Point c, double r): center(c.x, c.y)
{
    radius = r;
}

Circle::Circle(double x, double y, double r) : center(x, y)
{
    radius = r;
}

Circle::~Circle()
{
}

double Circle::getRadius()
{
   return radius;
}

Point Circle::getCenter()
{
      return center;
}

Line Line::getPerpendicular(Point p)
{
     if(isVertical())
     {
          if(p.x == 0)
          {
               Point q(1.0, p.y);
               Line l(p, q);
               return l;
          }
          else
          {
               Point q(0.0, p.y);
               Line l(p, q);
               return l;
          }
     }
     else if(getSlope() == 0)
     {
          if(p.y == 0)
          {
               Point q(p.x, 1.0);
               Line l(p, q);
               return l;
          }
          else
          {
               Point q(p.x, 0.0);
               Line l(p, q);
               return l;
          }
     }
     else
     {
          double newSlope = (-1) / getSlope();
          double newY = p.y + newSlope;
          double newX = p.x + 1;
          Point q(newX, newY);
          Line l(p, q);
          return l;
     }
}

void Circle::setRadius(double r)
{
     radius = r;
}

void Circle::setCenter(Point c)
{
     center = c;
}

void Circle::setCenter(double x, double y)
{
     center.x = x;
     center.y = y;
}
