# Read performance csv and calculate mean and standard deviation per grid size.
args = commandArgs(trailingOnly=TRUE)
data <- read.csv(args[1], header=TRUE)
agg  <- do.call( data.frame, aggregate( . ~ size, data, function(x) c(mean=mean(x), sd=round(sd(x)))))
agg