x <- data.frame(replicate(2, rnorm( 200, 50, 6 )))
x <- data.frame(rnorm(200, 50, 6), rnorm(200, 50, 6))
y <- x[ x[,1]>0 & x[,1]<100 & x[,2]>0 & x[,2]<100, ]
write.table(y, file="data", sep=" ", row.names=F, col.names=F)
