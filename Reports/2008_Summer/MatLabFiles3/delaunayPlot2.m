function delaunayPlot2(filename)

%This function is to relate the initial positiveness of a triangulation
%with its end result. This can be used to ensure that the flip algorithm is
%working correctly. The input is a single file name, much like the regular
%delaunayPlot. 
%
%We produce a graph by determining the extremes of a triangulation in the
%plane, and construct a meshgrid rectangle for all possible points. While
%this can be computationally expensive, it was the first thing we could
%think of that worked. For each point in the grid, it determines if it
%lies inside a face of the triangulation by using the program isInside. We
%can accumulate the resulting pixels relating them to the positive ot
%negative Delaunay triangles that they are found inside of. 

%Read from file from text, determine number of triangles. 
K = textread(filename);
S = (size(K,1))/4;

%Determine range of coordinates, create a meshgrid of 40401 points. 
XMIN = min(K(:,2));
XMAX = max(K(:,2));
Xdelta = (XMAX - XMIN)/200;
YMIN = min(K(:,3));
YMAX = max(K(:,3));
Ydelta = (YMAX - YMIN)/200;
[X,Y] = meshgrid(XMIN:Xdelta:XMAX, YMIN:Ydelta:YMAX);

%Declare constants, value trackers. the value 'res' is a vector of the
%results of each grid point through the loop. 
res = [];
resdat0 = 0;
resdat1 = 0;
resdatn1 = 0;
resdat2 = 0;
resdatn2 = 0;
resdat3 = 0;
resdatn3 = 0;
resdat4 = 0;
total = 0;

%Since this program takes a while to run, we implemented a progress chart
%of sorts, displaying the percentage completed in the process. 
fives = 0;

%This loop takes every grid point and performs multiple things. 

for i = 1:size(X,1)*size(X,2)
    
    %About every 5% or so, we run into this if statement which will update
    %the progress screen accordingly. 
    
    if mod(i, round(size(X,1)*size(X,2)/20)) == 0
        fives = fives + 5;
        fprintf('%G \n',fives)
        if fives == 100
            fprintf('Generating graph...');
        end
    end
    
    %'count' keeps track of whther a point is inside a positive or negative
    %triangle, and is accumulated over all possible faces. 'useful' is a
    %counter to determine which grid points are actually inside any
    %triangles. 
    
    count = 0;
    useful = 0;
    
    %Each grid point is tested to see if it is inside each particular face
    %using the 'isInside' method. If so, 'count' and 'useful' are updated
    %accordingly. 
    
    for j = 0:S-1
        X1 = [K((j)*4 + 2,2) K((j)*4 + 3,2) K((j)*4 + 4,2) K((j)*4 + 2,2)];
        Y1 = [K((j)*4 + 2,3) K((j)*4 + 3,3) K((j)*4 + 4,3) K((j)*4 + 2,3)];
        if (isInside(X(i),Y(i),X1,Y1) == 1)
            count = count + K(j*4 + 1, 1);
            useful = useful + 1;
        end
    end
 
%If a point was found to be inside at least one triangle, we do more things
%with it. We update 'res' by adding its 'count' value to the end and plot
%the point a certain color depending on its 'count' value. Blue and red are
%used to depict 0's and 1's, respectively. Theoretically, these should be
%the only colors on the graph, but we also include values of 2-4 for the
%possibility of overlap during the triangulation process. 

    if useful > 0
        res = [res count];
        if count == 1
            plot(X(i),Y(i),'r.')
            resdat1 = resdat1 + 1;
            total = total + count; 
        end
        if count == 2
            plot(X(i),Y(i),'m.')
            resdat2 = resdat2 + 1;
            total = total + count; 
        end
        if count == 3
            plot(X(i),Y(i),'m.')
            resdat3 = resdat3 + 1;
            total = total + count; 
        end
        if count == 4
            plot(X(i),Y(i),'k.')
            resdat4 = resdat4 + 1;
            total = total + count; 
        end
        if count == 0
             plot(X(i),Y(i),'b.')
             resdat0 = resdat0 + 1;
             total = total + count; 
        end
        if count == -1
             plot(X(i),Y(i),'g.')
             resdatn1 = resdatn1 + 1;
             total = total + count; 
        end
        if count == -2
            plot(X(i),Y(i),'y.')
            resdatn2 = resdatn2 + 1;
            total = total + count;
        end
        if count == -3
            plot(X(i),Y(i),'k.')
            resdatn3 = resdatn3 + 1;
            total = total + count; 
        end
    hold on;
    end
    
end

%Display the original delaunayPlot of the triangulation to see how colored
%regions coincide with the presence of negative triangles and the like.

delaunayPlot(filename,'k')

%Outputs the span of values obtained through the whole process, as well as
%the total count tally. 

nn = unique(res)
total

%At the end, relay the number of individual points with a specific count
%value. As data is collected for values from -3 to 4, any values outside
%that range will be 'unaccounted for'. 
%
%We have observed that the number of pixels of odd 'count' numbers remain
%constant for any particular triangulation during its flipping process.

for i = min(nn):max(nn)
        fprintf('number of %G''s is: ', i);
        if i >= -3 && i <= 4
            if i == -3
                fprintf('%G \n', resdatn3)
            end
            if i == -2
                fprintf('%G \n', resdatn2)
            end
            if i == -1
                fprintf('%G \n', resdatn1)
            end
            if i == 0
                fprintf('%G \n', resdat0)
            end
            if i == 1
                fprintf('%G \n', resdat1)
            end
            if i == 2
                fprintf('%G \n', resdat2)
            end
            if i == 3
                fprintf('%G \n', resdat3)
            end
            if i == 4
                fprintf('%G \n', resdat4)
            end
        else
            fprintf('unaccounted for\n')
        end
end