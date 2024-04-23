import matplotlib.pyplot as plt

# Data for the three plots
x_values = ['1','3','5','9']
y1_values = [0.3649, 0.2983, 0.2424, 0.207]
y2_values = [1.1433, 0.9718, 0.9302, 0.8826]
y3_values = [5.5161, 5.2466, 4.3526, 0.38743]

# Create the line graph
plt.plot(x_values, y1_values, label='1 million', marker='o')
plt.plot(x_values, y2_values, label='2 million', marker='o')
plt.plot(x_values, y3_values, label='5 million', marker='o')

# Add labels and title
plt.xlabel('Number of Threads')
plt.ylabel('Running Time(s)')
plt.title('MSQSA')

# Add a legend
plt.legend()

# Display the plot
plt.show()

