# Created:  Thu 19 Jun 2014 12:19 PM
# x <- data.frame(replicate(2, rnorm( 200, 50, 6 )))
#x <- data.frame(rnorm(200, 75, 6), rnorm(200, 25, 6))
#y <- x[ x[,1]>0 & x[,1]<100 & x[,2]>0 & x[,2]<100, ]
#write.table(y, file="data", sep=" ", row.names=F, col.names=F)

simcor <- function (n, xmean, xsd, ymean, ysd, correlation) {
	x <- rnorm(n)
	y <- rnorm(n)
	z <- correlation * scale(x)[,1] + sqrt(1 - correlation^2) *
			 scale(resid(lm(y ~ x)))[,1]
	xresult <- xmean + xsd * scale(x)[,1]
	yresult <- ymean + ysd * z
	data.frame(x=xresult,y=yresult)
}

x <- simcor(1000, 50, 65, 50, 65, 0.999)
y <- x[ x[,1]>0 & x[,1]<100 & x[,2]>0 & x[,2]<100, ]
plot(y)

write.table(y, file="data", sep=" ", row.names=F, col.names=F)

x2 <- simcor(1000, 50, 8, 50, 70, 0.999)
y2 <- x2[ x2[,1]>0 & x2[,1]<100 & x2[,2]>0 & x2[,2]<100, ]
points(y2)

write.table(y2, file="data", append=T, sep=" ", row.names=F, col.names=F)

x3 <- simcor(1000, 50, 70, 50, 8, 0.999)
x3 <- x3 + 30
y3 <- x3[ x3[,1]>0 & x3[,1]<100 & x3[,2]>0 & x3[,2]<100, ]
points(y3)

write.table(y3, file="data", append=T, sep=" ", row.names=F, col.names=F)
