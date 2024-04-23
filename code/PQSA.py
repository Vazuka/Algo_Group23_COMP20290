import matplotlib.pyplot as plt

# Data for the three plots
x_values = ['1','3','5','9']
y1_values = [1.5558, 1.478, 1.409, 1.387]
y2_values = [5.444, 5.423, 5.398, 5.363]
y3_values = [19.467, 17.420, 16.291, 15.964]

# Create the line graph
plt.plot(x_values, y1_values, label='1 million', marker='o')
plt.plot(x_values, y2_values, label='2 million', marker='o')
plt.plot(x_values, y3_values, label='5 million', marker='o')

# Add labels and title
plt.xlabel('Number of Threads')
plt.ylabel('Running Time(s)')
plt.title('PQSA')

# Add a legend
plt.legend()

# Display the plot
plt.show()
