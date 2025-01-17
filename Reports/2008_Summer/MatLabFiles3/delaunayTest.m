%This file generates a non-weighted Delaunay triangulation for a given set
%of points. The circumcircles are also drawn to show their Delaunayness.
%Points may be randomly generated to explicitly set to draw the desired
%figure. 

%Generate coordinates from random or self-input. 
r = 5;
% X = normrnd(0,20,r,1);
% Y = normrnd(0,20,r,1);
%X = rand(r,1);
%Y = rand(r,1);
X = [0 0 1 1 .5];
Y = [0 1 1 0 .5];

%Using the function 'delaunay', create a triangulation using the given
%points. The result is an Nx3 matrix of vertices. 
TRI = delaunay(X,Y);

for i = 1:size(TRI,1)
    v1 = TRI(i,1);
    v2 = TRI(i,2);
    v3 = TRI(i,3);
    Temp = [v1 v2 v3];
    XTemp = [X(v1), X(v2), X(v3), X(v1)];
    YTemp = [Y(v1), Y(v2), Y(v3), Y(v1)];
    plot(XTemp,YTemp,'k+',XTemp, YTemp,'b','linewidth',2);
    hold on;
    th = 0:.001:2*pi;
a = X(v1); b = Y(v1); c = X(v2); d = Y(v2); e = X(v3); f = Y(v3);
ycn = ((a^2 - c^2 - d^2 + b^2)*(c-e) - (c^2 - e^2 - f^2 + d^2)*(a-c));
ycd = 2*(f - d)*(a-c) - 2*(d - b)*(c-e);
yc = ycn/ycd;
xc = (a^2 - c^2 - d^2 + b^2 + 2*yc*(d - b))/(2*(a-c));
r = sqrt((xc - a)^2 + (yc - b)^2);
P = xc + r*cos(th);
Q = yc + r*sin(th);
plot(P,Q,'r')
end
axis image


